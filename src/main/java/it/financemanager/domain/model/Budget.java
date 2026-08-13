package it.financemanager.domain.model;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
public record Budget(Long id, Long userId, Long categoryId, String categoryName, BigDecimal amount, LocalDate startDate,
    LocalDate endDate, Instant createdAt, Instant updatedAt) {}
