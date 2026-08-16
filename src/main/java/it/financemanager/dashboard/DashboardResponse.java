package it.financemanager.dashboard;

import it.financemanager.transaction.TransactionResponse;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record DashboardResponse(LocalDate from, LocalDate to,
                                BigDecimal totalIncome,
                                BigDecimal totalExpenses, BigDecimal balance,
                                long transactionCount,
                                List<CategoryExpense> expensesByCategory,
                                List<DailyCashFlow> dailyCashFlow,
                                List<TransactionResponse> recentTransactions) {
  public record CategoryExpense(Long categoryId, String categoryName,
                                String categoryColor, BigDecimal amount,
                                BigDecimal percentage) {}

  public record DailyCashFlow(LocalDate date, BigDecimal income,
                              BigDecimal expenses, BigDecimal balance) {}
}
