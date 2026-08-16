package it.financemanager.transaction;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Framework-neutral command shared by create and update transaction use cases.
 */
public record SaveTransactionCommand(Long categoryId, TransactionType type,
                                     BigDecimal amount, LocalDate date,
                                     String description) {}
