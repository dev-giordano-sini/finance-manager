package it.financemanager.infrastructure.web.budget;

import it.financemanager.budget.*;
import jakarta.validation.constraints.*; import java.math.BigDecimal; import java.time.LocalDate;
public record BudgetRequest(@NotNull Long categoryId,@NotNull @DecimalMin("0.01") @Digits(integer=17,fraction=2) BigDecimal amount,@NotNull LocalDate startDate,@NotNull LocalDate endDate) {}
