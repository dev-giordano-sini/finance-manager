# Finance Manager API

Production-oriented personal finance REST API built with Java 21, Spring Boot 3, PostgreSQL, Flyway, Spring Security, and JWT.

## Features

- Register and login with BCrypt password hashing and stateless signed JWT access tokens (no refresh tokens)
- User-isolated CRUD for categories, transactions, and budgets
- Bean Validation at the API boundary and database constraints for defense in depth
- RFC 9457 `ProblemDetail` error responses
- Flyway-owned PostgreSQL schema; Hibernate runs in validation-only mode
- OpenAPI 3 documentation with Swagger UI
- Multi-stage, non-root Docker image and a health-checked Compose stack
- Optimistic locking and automatic UTC audit timestamps on all entities

## Prerequisites

- Java 21
- Maven 3.9+
- PostgreSQL 16+, or Docker with Docker Compose

## Run with Docker Compose

Use a secret of at least 32 bytes. In production, generate and store a high-entropy value in a secrets manager rather than committing it.

```bash
export JWT_SECRET="replace-this-with-a-random-secret-of-at-least-32-bytes"
docker compose up --build
```

The API is available at `http://localhost:8080`; Swagger UI is at `http://localhost:8080/swagger-ui.html`.

## Run locally

Start PostgreSQL and configure the environment (shown values match the Compose database):

```bash
export DB_URL=jdbc:postgresql://localhost:5432/finance_manager
export DB_USERNAME=finance
export DB_PASSWORD=finance
export JWT_SECRET="replace-this-with-a-random-secret-of-at-least-32-bytes"
mvn spring-boot:run
```

Flyway applies migrations automatically. The application never uses Hibernate to create or modify the schema.

## Authentication

Register:

```bash
curl -X POST http://localhost:8080/api/v1/auth/register \
  -H 'Content-Type: application/json' \
  -d '{"email":"user@example.com","password":"strong-password","name":"Example User"}'
```

Login uses `POST /api/v1/auth/login` with `email` and `password`. Both endpoints return an `accessToken`. Supply it to protected endpoints:

```bash
curl http://localhost:8080/api/v1/categories \
  -H "Authorization: Bearer $TOKEN"
```

## API overview

| Resource | Endpoints |
| --- | --- |
| Authentication | `POST /api/v1/auth/register`, `POST /api/v1/auth/login` |
| Categories | `GET, POST /api/v1/categories`; `GET, PUT, DELETE /api/v1/categories/{id}` |
| Transactions | `GET, POST /api/v1/transactions`; `GET, PUT, DELETE /api/v1/transactions/{id}` |
| Budgets | `GET, POST /api/v1/budgets`; `GET, PUT, DELETE /api/v1/budgets/{id}` |

Transactions support `from`, `to`, `page`, `size`, and `sort` query parameters. Page size is capped at 100. Monetary amounts are positive decimals with two fractional digits. Dates use ISO-8601 (`YYYY-MM-DD`). A user can only reference and access their own data; inaccessible IDs deliberately return 404.

The full interactive contract is exposed by Swagger UI and the OpenAPI document at `/v3/api-docs`.

## Build and test

```bash
mvn clean verify
```

## Configuration

| Variable | Default | Purpose |
| --- | --- | --- |
| `DB_URL` | `jdbc:postgresql://localhost:5432/finance_manager` | JDBC connection URL |
| `DB_USERNAME` | `finance` | Database username |
| `DB_PASSWORD` | `finance` | Database password |
| `JWT_SECRET` | development-only value | HMAC key; must be at least 32 bytes |
| `JWT_EXPIRATION` | `3600s` | Access-token lifetime (Spring duration syntax) |
| `SERVER_PORT` | `8080` | HTTP port |

Always override database credentials and `JWT_SECRET` in non-development environments. Terminate TLS at an ingress/reverse proxy, restrict Swagger as appropriate, and back up the PostgreSQL volume.

## Architecture

Code is package-by-feature under `it.financemanager`: `auth`, `user`, `category`, `transaction`, and `budget`. Cross-cutting configuration, security, persistence base types, and errors live under `common`. DTOs are immutable Java records and mapping is explicit. Entities use `Long` identity IDs, optimistic versions, and immutable creation/modification audit metadata.
