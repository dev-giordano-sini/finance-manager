package it.financemanager.category;

import java.time.Instant;
public record CategoryResponse(Long id, String name, String color,
                               Instant createdAt, Instant updatedAt) {}
