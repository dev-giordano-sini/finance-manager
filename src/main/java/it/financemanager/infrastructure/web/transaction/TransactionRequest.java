package it.financemanager.infrastructure.web.transaction;

import it.financemanager.transaction.TransactionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionRequest(@NotNull Long categoryId,
                                 @NotNull TransactionType type,
                                 @NotNull @DecimalMin(value = "0.01")
                                 @Digits(integer = 17, fraction = 2)
                                 BigDecimal amount, @NotNull LocalDate date,
                                 @Size(max = 500) String description) {
}
