package it.financemanager.infrastructure.web.transaction;

import it.financemanager.transaction.*;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionRequest(@NotNull Long categoryId, @NotNull TransactionType type,
        @NotNull @DecimalMin(value = "0.01") @Digits(integer = 17, fraction = 2) BigDecimal amount,
        @NotNull LocalDate date, @Size(max = 500) String description) { }
