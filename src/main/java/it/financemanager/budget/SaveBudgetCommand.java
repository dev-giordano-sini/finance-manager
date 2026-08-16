package it.financemanager.budget;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Framework-neutral command shared by create and update budget use cases.
 */
public record SaveBudgetCommand(Long categoryId, BigDecimal amount,
                                LocalDate startDate, LocalDate endDate) {
}
