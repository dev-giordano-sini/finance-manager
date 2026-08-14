package it.financemanager.dashboard;

import it.financemanager.category.Category;
import it.financemanager.common.BaseEntity;
import it.financemanager.transaction.Transaction;
import it.financemanager.transaction.TransactionStore;
import it.financemanager.transaction.TransactionType;
import it.financemanager.user.CurrentUserService;
import it.financemanager.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DashboardServiceTest {
    private static final LocalDate TODAY = LocalDate.of(2026, 8, 10);

    @Mock
    private TransactionStore transactions;
    @Mock
    private CurrentUserService currentUser;

    private DashboardService service;
    private User user;
    private Category groceries;
    private Category home;

    @BeforeEach
    void setUp() {
        Clock clock = Clock.fixed(Instant.parse("2026-08-10T12:00:00Z"), ZoneOffset.UTC);
        service = new DashboardService(transactions, currentUser, clock);
        user = new User("user@example.com", "password", "User", "Example", null);
        groceries = new Category(user, "Spesa", "#FF0000");
        home = new Category(user, "Casa", "#00FF00");
        setId(user, 7L);
        setId(groceries, 11L);
        setId(home, 12L);
        when(currentUser.get()).thenReturn(user);
    }

    @Test
    void buildsCompleteDashboardForSelectedPeriod() {
        LocalDate from = LocalDate.of(2026, 8, 1);
        Transaction newestExpense = transaction(3L, groceries, TransactionType.EXPENSE, "25.25", TODAY, "Supermercato");
        Transaction income = transaction(2L, home, TransactionType.INCOME, "100.00", LocalDate.of(2026, 8, 8), "Stipendio");
        Transaction olderExpense = transaction(1L, home, TransactionType.EXPENSE, "24.75", LocalDate.of(2026, 8, 8), "Bolletta");
        when(transactions.findForDashboard(7L, from, TODAY))
                .thenReturn(List.of(newestExpense, income, olderExpense));

        DashboardResponse result = service.get(from, TODAY);

        assertThat(result.totalIncome()).isEqualByComparingTo("100.00");
        assertThat(result.totalExpenses()).isEqualByComparingTo("50.00");
        assertThat(result.balance()).isEqualByComparingTo("50.00");
        assertThat(result.transactionCount()).isEqualTo(3);
        assertThat(result.expensesByCategory()).extracting(DashboardResponse.CategoryExpense::categoryName)
                .containsExactly("Spesa", "Casa");
        assertThat(result.expensesByCategory()).extracting(DashboardResponse.CategoryExpense::percentage)
                .containsExactly(new BigDecimal("50.50"), new BigDecimal("49.50"));
        assertThat(result.dailyCashFlow()).hasSize(10);
        assertThat(result.dailyCashFlow().get(7).balance()).isEqualByComparingTo("75.25");
        assertThat(result.dailyCashFlow().get(8).balance()).isEqualByComparingTo("0.00");
        assertThat(result.recentTransactions()).extracting(item -> item.id()).containsExactly(3L, 2L, 1L);
    }

    @Test
    void defaultsToCurrentMonthAndReturnsZeroFilledDays() {
        LocalDate firstDay = TODAY.withDayOfMonth(1);
        when(transactions.findForDashboard(7L, firstDay, TODAY)).thenReturn(List.of());

        DashboardResponse result = service.get(null, null);

        assertThat(result.from()).isEqualTo(firstDay);
        assertThat(result.to()).isEqualTo(TODAY);
        assertThat(result.totalIncome()).isEqualByComparingTo("0.00");
        assertThat(result.totalExpenses()).isEqualByComparingTo("0.00");
        assertThat(result.dailyCashFlow()).hasSize(10)
                .allSatisfy(day -> {
                    assertThat(day.income()).isEqualByComparingTo("0.00");
                    assertThat(day.expenses()).isEqualByComparingTo("0.00");
                });
        verify(transactions).findForDashboard(7L, firstDay, TODAY);
    }

    @Test
    void rejectsInvertedPeriodWithoutQueryingCurrentUser() {
        assertThatThrownBy(() -> service.get(TODAY, TODAY.minusDays(1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("from must not be after to");
    }

    private Transaction transaction(Long id, Category category, TransactionType type,
                                    String amount, LocalDate date, String description) {
        Transaction transaction = new Transaction(user, category, type, new BigDecimal(amount), date, description);
        setId(transaction, id);
        return transaction;
    }

    private void setId(BaseEntity entity, Long id) {
        ReflectionTestUtils.setField(entity, "id", id);
    }
}
