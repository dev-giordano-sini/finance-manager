package it.financemanager.domain.model;
import java.time.Instant;
public record User(Long id, String email, String passwordHash, String name, String surname, String role,
                   Instant createdAt, Instant updatedAt) { }
