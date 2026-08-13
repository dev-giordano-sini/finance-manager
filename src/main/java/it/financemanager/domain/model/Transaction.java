package it.financemanager.domain.model;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
public record Transaction(Long id, Long userId, Long categoryId, String categoryName, String categoryColor,
    TransactionType type, BigDecimal amount, LocalDate date, String description, Instant createdAt, Instant updatedAt) {
}
