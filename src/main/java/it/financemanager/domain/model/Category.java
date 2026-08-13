package it.financemanager.domain.model;
import java.time.Instant;
public record Category(Long id, Long userId, String name, String color, Instant createdAt, Instant updatedAt) { }
