package it.financemanager.infrastructure.web.budget;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record BudgetRequest(@NotNull Long categoryId,
                            @NotNull @DecimalMin("0.01")
                            @Digits(integer = 17, fraction = 2)
                            BigDecimal amount, @NotNull LocalDate startDate,
                            @NotNull LocalDate endDate) {
}
