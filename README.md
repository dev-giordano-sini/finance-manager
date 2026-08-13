# Finance Manager

Applicazione full-stack per la gestione delle finanze personali. Il backend espone una REST API in Java 21/Spring Boot, mentre il frontend separato in `frontend/` usa Vue 3, Vite e TypeScript.

## Features

- Register and login with BCrypt password hashing and stateless signed JWT access tokens (no refresh tokens)
- User-isolated CRUD for categories, transactions, and budgets
- Dashboard summaries with cash flow, category spending, and recent transactions
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
- Node.js 20+ e npm (per il frontend)

## Run with Docker Compose

Use a secret of at least 32 bytes. In production, generate and store a high-entropy value in a secrets manager rather than committing it.

```bash
export JWT_SECRET="replace-this-with-a-random-secret-of-at-least-32-bytes"
docker compose up --build
```

The API is available at `http://localhost:8080`; Swagger UI is at `http://localhost:8080/swagger-ui.html`.

Il container Compose avvia il backend e il database. Per avviare anche il frontend, segui la sezione seguente.

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

## Avviare il frontend

Con il backend in ascolto su `http://localhost:8080`, apri un secondo terminale:

```bash
cd frontend
npm install
npm run dev
```

Apri `http://localhost:5173`. In sviluppo Vite inoltra le richieste `/api` al backend, quindi il browser comunica esclusivamente con le REST API esistenti. Per usare un endpoint differente, copia `.env.example` in `.env` e imposta `VITE_API_URL` (ad esempio `https://api.example.com/api/v1`).

Per creare la build di produzione:

```bash
cd frontend
npm run build
```

Gli asset statici vengono generati in `frontend/dist/`. Il frontend è un'applicazione indipendente e non viene incorporato nel jar Spring Boot.

## Authentication

Register:

```bash
curl -X POST http://localhost:8080/api/v1/auth/register \
  -H 'Content-Type: application/json' \
  -d '{"email":"user@example.com","password":"strong-password","name":"Example","surname":"User"}'
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
| Dashboard | `GET /api/v1/dashboard` |

Transactions support `from`, `to`, `page`, and `size` query parameters and are sorted by date descending. Page size is capped at 100. Monetary amounts are positive decimals with two fractional digits. Dates use ISO-8601 (`YYYY-MM-DD`). A user can only reference and access their own data; inaccessible IDs deliberately return 404.

The dashboard accepts optional ISO-8601 `from` and `to` parameters and defaults to the current month through today. It returns income, expenses, balance, expense totals by category, daily cash flow, and the five most recent transactions for the selected period.

The full interactive contract is exposed by Swagger UI and the OpenAPI document at `/v3/api-docs`.

## Build and test

```bash
mvn clean verify
```

Controllo TypeScript e build del frontend:

```bash
cd frontend
npm run type-check
npm run build
```

## Configuration

| Variable | Default | Purpose |
| --- | --- | --- |
| `DB_URL` | `jdbc:postgresql://localhost:5432/finance_manager` | JDBC connection URL |
| `DB_USERNAME` | `finance` | Database username |
| `DB_PASSWORD` | `finance` | Database password |
| `JWT_SECRET` | required | HMAC key; must be at least 32 bytes |
| `JWT_EXPIRATION` | `3600s` | Access-token lifetime (Spring duration syntax) |
| `SERVER_PORT` | `8080` | HTTP port |

Always override database credentials and `JWT_SECRET` in non-development environments. Terminate TLS at an ingress/reverse proxy, restrict Swagger as appropriate, and back up the PostgreSQL volume.

## Backend architecture

The backend follows a ports-and-adapters (hexagonal) architecture:

- `domain/model` contains framework-independent business models.
- `application/port/in` defines the operations exposed by the application.
- `application/port/out` defines infrastructure capabilities required by use cases.
- `application/service` implements use cases without Spring or persistence dependencies.
- `infrastructure/web` contains inbound REST adapters and transport DTO mapping.
- `infrastructure/persistence` contains JPA entities, Spring Data repositories, and outbound adapters.
- `infrastructure/security` contains JWT and Spring Security adapters.
- `infrastructure/config` is the composition root that wires port implementations to use cases.

Dependencies point inward: infrastructure depends on application and domain, while domain and application do not depend on infrastructure or Spring.
