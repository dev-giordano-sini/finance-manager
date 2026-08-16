package it.financemanager.budget;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
public record BudgetResponse(Long id, Long categoryId, String categoryName,
                             BigDecimal amount, LocalDate startDate,
                             LocalDate endDate, Instant createdAt,
                             Instant updatedAt) {}
