package it.financemanager.dashboard;

import it.financemanager.transaction.Transaction;
import it.financemanager.transaction.TransactionRepository;
import it.financemanager.transaction.TransactionResponse;
import it.financemanager.transaction.TransactionType;
import it.financemanager.user.CurrentUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Clock;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
@Transactional(readOnly = true)
public class DashboardService implements DashboardUseCase {
    private static final BigDecimal ZERO = new BigDecimal("0.00");

    private final TransactionRepository transactions;
    private final CurrentUserService currentUser;
    private final Clock clock;

    public DashboardService(TransactionRepository transactions, CurrentUserService currentUser, Clock clock) {
        this.transactions = transactions;
        this.currentUser = currentUser;
        this.clock = clock;
    }

    public DashboardResponse get(LocalDate from, LocalDate to) {
        LocalDate resolvedTo = to == null ? LocalDate.now(clock) : to;
        LocalDate resolvedFrom = from == null ? resolvedTo.withDayOfMonth(1) : from;
        if (resolvedFrom.isAfter(resolvedTo)) {
            throw new IllegalArgumentException("from must not be after to");
        }

        List<Transaction> values = transactions.findForDashboard(
                currentUser.get().getId(), resolvedFrom, resolvedTo);
        BigDecimal income = total(values, TransactionType.INCOME);
        BigDecimal expenses = total(values, TransactionType.EXPENSE);

        return new DashboardResponse(
                resolvedFrom,
                resolvedTo,
                income,
                expenses,
                income.subtract(expenses),
                values.size(),
                categoryExpenses(values, expenses),
                dailyCashFlow(values, resolvedFrom, resolvedTo),
                values.stream().limit(5).map(this::map).toList());
    }

    private BigDecimal total(List<Transaction> values, TransactionType type) {
        return values.stream()
                .filter(value -> value.getType() == type)
                .map(Transaction::getAmount)
                .reduce(ZERO, BigDecimal::add);
    }

    private List<DashboardResponse.CategoryExpense> categoryExpenses(
            List<Transaction> values, BigDecimal totalExpenses) {
        Map<Long, CategoryTotal> totals = new LinkedHashMap<>();
        values.stream()
                .filter(value -> value.getType() == TransactionType.EXPENSE)
                .forEach(value -> totals.compute(
                        value.getCategory().getId(),
                        (id, current) -> current == null
                                ? new CategoryTotal(value.getCategory().getName(), value.getCategory().getColor(), value.getAmount())
                                : current.add(value.getAmount())));

        return totals.entrySet().stream()
                .map(entry -> new DashboardResponse.CategoryExpense(
                        entry.getKey(), entry.getValue().name(), entry.getValue().color(), entry.getValue().amount(),
                        percentage(entry.getValue().amount(), totalExpenses)))
                .sorted((left, right) -> right.amount().compareTo(left.amount()))
                .toList();
    }

    private BigDecimal percentage(BigDecimal amount, BigDecimal total) {
        if (total.signum() == 0) return ZERO;
        return amount.multiply(BigDecimal.valueOf(100)).divide(total, 2, RoundingMode.HALF_UP);
    }

    private List<DashboardResponse.DailyCashFlow> dailyCashFlow(
            List<Transaction> values, LocalDate from, LocalDate to) {
        Map<LocalDate, BigDecimal[]> totals = new TreeMap<>();
        values.forEach(value -> {
            BigDecimal[] day = totals.computeIfAbsent(value.getDate(), ignored -> new BigDecimal[]{ZERO, ZERO});
            int index = value.getType() == TransactionType.INCOME ? 0 : 1;
            day[index] = day[index].add(value.getAmount());
        });

        List<DashboardResponse.DailyCashFlow> result = new ArrayList<>();
        from.datesUntil(to.plusDays(1)).forEach(date -> {
            BigDecimal[] day = totals.getOrDefault(date, new BigDecimal[]{ZERO, ZERO});
            result.add(new DashboardResponse.DailyCashFlow(
                    date, day[0], day[1], day[0].subtract(day[1])));
        });
        return result;
    }

    private TransactionResponse map(Transaction transaction) {
        return new TransactionResponse(
                transaction.getId(), transaction.getCategory().getId(), transaction.getCategory().getName(),
                transaction.getType(), transaction.getAmount(), transaction.getDate(), transaction.getDescription(),
                transaction.getCreatedAt(), transaction.getUpdatedAt());
    }

    private record CategoryTotal(String name, String color, BigDecimal amount) {
        private CategoryTotal add(BigDecimal value) {
            return new CategoryTotal(name, color, amount.add(value));
        }
    }
}
