package it.financemanager.infrastructure.web.dto;
import it.financemanager.domain.model.TransactionType;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import java.util.List;
public final class ApiDtos {
    private ApiDtos() {}
    public record LoginRequest(@NotBlank @Email String email, @NotBlank String password) {}
    public record RegisterRequest(@NotBlank @Size(max = 100) String name, @NotBlank @Size(max = 100) String surname,
        @NotBlank @Email @Size(max = 254) String email, @NotBlank @Size(min = 8, max = 72) String password) {}
    public record AuthResponse(String accessToken, String tokenType, long expiresIn) {}
    public record CurrentUserResponse(String name, String surname, String email) {}
    public record CategoryRequest(
        @NotBlank @Size(max = 80) String name, @NotBlank @Pattern(regexp = "^#[0-9A-Fa-f]{6}$") String color) {}
    public record CategoryResponse(Long id, String name, String color, Instant createdAt, Instant updatedAt) {}
    public record BudgetRequest(@NotNull Long categoryId, @NotNull @DecimalMin("0.01") BigDecimal amount,
        @NotNull LocalDate startDate, @NotNull LocalDate endDate) {}
    public record BudgetResponse(Long id, Long categoryId, String categoryName, BigDecimal amount, LocalDate startDate,
        LocalDate endDate, Instant createdAt, Instant updatedAt) {}
    public record TransactionRequest(@NotNull Long categoryId, @NotNull TransactionType type,
        @NotNull @DecimalMin("0.01") BigDecimal amount, @NotNull LocalDate date, @Size(max = 500) String description) {}
    public record TransactionResponse(Long id, Long categoryId, String categoryName, TransactionType type,
        BigDecimal amount, LocalDate date, String description, Instant createdAt, Instant updatedAt) {}
    public record PageResponse<T>(List<T> content, int number, int size, long totalElements, int totalPages) {}
    public record CategoryExpense(
        Long categoryId, String categoryName, String categoryColor, BigDecimal amount, BigDecimal percentage) {}
    public record DailyCashFlow(LocalDate date, BigDecimal income, BigDecimal expenses, BigDecimal balance) {}
    public record DashboardResponse(LocalDate from, LocalDate to, BigDecimal totalIncome, BigDecimal totalExpenses,
        BigDecimal balance, long transactionCount, List<CategoryExpense> expensesByCategory,
        List<DailyCashFlow> dailyCashFlow, List<TransactionResponse> recentTransactions) {}
}
