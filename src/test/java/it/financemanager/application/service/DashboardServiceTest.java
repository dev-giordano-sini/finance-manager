package it.financemanager.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import it.financemanager.application.model.PageResult;
import it.financemanager.application.port.out.TransactionPort;
import it.financemanager.application.port.out.UserPort;
import it.financemanager.domain.model.Transaction;
import it.financemanager.domain.model.TransactionType;
import it.financemanager.domain.model.User;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class DashboardServiceTest {
    private static final LocalDate TODAY = LocalDate.of(2026, 8, 10);
    private static final User USER =
        new User(7L, "user@example.com", "hash", "User", "Name", "USER", Instant.EPOCH, Instant.EPOCH);

    @Test
    void aggregatesSelectedPeriodAndZeroFillsMissingDays() {
        TransactionPort transactions = new StubTransactions(
            List.of(transaction(3L, 11L, "Food", "#FF0000", TransactionType.EXPENSE, "25.25", TODAY),
                transaction(2L, 12L, "Salary", "#00FF00", TransactionType.INCOME, "100.00", TODAY.minusDays(2)),
                transaction(1L, 11L, "Food", "#FF0000", TransactionType.EXPENSE, "24.75", TODAY.minusDays(2))));
        DashboardService service = service(transactions);

        var result = service.get(LocalDate.of(2026, 8, 1), TODAY);

        assertThat(result.totalIncome()).isEqualByComparingTo("100.00");
        assertThat(result.totalExpenses()).isEqualByComparingTo("50.00");
        assertThat(result.balance()).isEqualByComparingTo("50.00");
        assertThat(result.dailyCashFlow()).hasSize(10);
        assertThat(result.expensesByCategory()).singleElement().satisfies(category -> {
            assertThat(category.categoryName()).isEqualTo("Food");
            assertThat(category.percentage()).isEqualByComparingTo("100.00");
        });
        assertThat(result.recentTransactions()).extracting(Transaction::id).containsExactly(3L, 2L, 1L);
    }

    @Test
    void rejectsInvertedPeriodBeforeUsingAdapters() {
        DashboardService service = service(new StubTransactions(List.of()));
        assertThatThrownBy(() -> service.get(TODAY, TODAY.minusDays(1)))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("from must not be after to");
    }

    private DashboardService service(TransactionPort transactions) {
        UserPort users = new UserPort() {
            public Optional<User> findByEmail(String email) {
                return Optional.of(USER);
            }
            public boolean existsByEmail(String email) {
                return true;
            }
            public User create(String email, String hash, String name, String surname, String role) {
                throw new UnsupportedOperationException();
            }
        };
        return new DashboardService(transactions,
            () -> USER.email(), users, Clock.fixed(Instant.parse("2026-08-10T12:00:00Z"), ZoneOffset.UTC));
    }

    private static Transaction transaction(Long id, Long categoryId, String categoryName, String color,
        TransactionType type, String amount, LocalDate date) {
        return new Transaction(id, USER.id(), categoryId, categoryName, color, type, new BigDecimal(amount), date, null,
            Instant.EPOCH, Instant.EPOCH);
    }

    private record StubTransactions(List<Transaction> values) implements TransactionPort {
        public List<Transaction> findForDashboard(Long userId, LocalDate from, LocalDate to) {
            return values;
        }
        public PageResult<Transaction> findAll(Long userId, LocalDate from, LocalDate to, int page, int size) {
            throw new UnsupportedOperationException();
        }
        public Optional<Transaction> findByIdAndUser(Long id, Long userId) {
            return Optional.empty();
        }
        public Transaction create(
            Long userId, Long categoryId, TransactionType type, BigDecimal amount, LocalDate date, String description) {
            throw new UnsupportedOperationException();
        }
        public Transaction update(Transaction transaction, Long categoryId, TransactionType type, BigDecimal amount,
            LocalDate date, String description) {
            throw new UnsupportedOperationException();
        }
        public void delete(Transaction transaction) {
            throw new UnsupportedOperationException();
        }
    }
}
