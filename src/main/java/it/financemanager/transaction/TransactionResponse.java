package it.financemanager.transaction;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public record TransactionResponse(Long id, Long categoryId, String categoryName,
                                  TransactionType type, BigDecimal amount,
                                  LocalDate date, String description,
                                  Instant createdAt, Instant updatedAt) {
}
