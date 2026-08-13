package it.financemanager.application.port.in;
import it.financemanager.domain.model.Transaction;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
public interface DashboardUseCase {
    record CategoryExpense(
        Long categoryId, String categoryName, String categoryColor, BigDecimal amount, BigDecimal percentage) {}
    record DailyCashFlow(LocalDate date, BigDecimal income, BigDecimal expenses, BigDecimal balance) {}
    record Dashboard(LocalDate from, LocalDate to, BigDecimal totalIncome, BigDecimal totalExpenses, BigDecimal balance,
        long transactionCount, List<CategoryExpense> expensesByCategory, List<DailyCashFlow> dailyCashFlow,
        List<Transaction> recentTransactions) {}
    Dashboard get(LocalDate from, LocalDate to);
}
