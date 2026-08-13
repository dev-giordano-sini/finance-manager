package it.financemanager.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import it.financemanager.application.model.PageResult;
import it.financemanager.application.port.out.*;
import it.financemanager.domain.model.*;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionServiceTest {
    private final User user =
        new User(7L, "user@example.com", "hash", "User", "Name", "USER", Instant.EPOCH, Instant.EPOCH);
    private final Category category = new Category(11L, 7L, "Food", "#112233", Instant.EPOCH, Instant.EPOCH);
    private final StubTransactions transactions = new StubTransactions();
    private TransactionService service;

    @BeforeEach
    void setUp() {
        UserPort users = new UserPort() {
            public Optional<User> findByEmail(String email) {
                return Optional.of(user);
            }
            public boolean existsByEmail(String email) {
                return true;
            }
            public User create(String e, String p, String n, String s, String r) {
                throw new UnsupportedOperationException();
            }
        };
        CategoryPort categories = new CategoryPort() {
            public Optional<Category> findByIdAndUser(Long id, Long uid) {
                return id.equals(11L) && uid.equals(7L) ? Optional.of(category) : Optional.empty();
            }
            public List<Category> findAllByUser(Long id) {
                return List.of(category);
            }
            public boolean existsByName(Long id, String n) {
                return false;
            }
            public Category create(Long id, String n, String c) {
                throw new UnsupportedOperationException();
            }
            public Category update(Category c, String n, String color) {
                throw new UnsupportedOperationException();
            }
            public void delete(Category c) {
                throw new UnsupportedOperationException();
            }
        };
        service = new TransactionService(transactions, categories, () -> "user@example.com", users);
    }

    @Test
    void createsOwnedTransactionAndNormalizesDescription() {
        Transaction result = service.create(
            11L, TransactionType.EXPENSE, new BigDecimal("12.50"), LocalDate.of(2026, 8, 1), "  lunch  ");
        assertThat(result.userId()).isEqualTo(7L);
        assertThat(result.description()).isEqualTo("lunch");
    }

    @Test
    void rejectsInvalidPaginationBeforeCallingPersistence() {
        assertThatThrownBy(() -> service.list(LocalDate.MIN, LocalDate.MAX, -1, 20))
            .hasMessage("page must not be negative");
        assertThatThrownBy(() -> service.list(LocalDate.MIN, LocalDate.MAX, 0, 101))
            .hasMessage("size must be between 1 and 100");
        assertThat(transactions.findAllCalls).isZero();
    }

    private static final class StubTransactions implements TransactionPort {
        int findAllCalls;
        public PageResult<Transaction> findAll(Long uid, LocalDate from, LocalDate to, int page, int size) {
            findAllCalls++;
            return new PageResult<>(List.of(), page, size, 0, 0);
        }
        public List<Transaction> findForDashboard(Long uid, LocalDate from, LocalDate to) {
            return List.of();
        }
        public Optional<Transaction> findByIdAndUser(Long id, Long uid) {
            return Optional.empty();
        }
        public Transaction create(
            Long uid, Long cid, TransactionType type, BigDecimal amount, LocalDate date, String description) {
            return new Transaction(
                1L, uid, cid, "Food", "#112233", type, amount, date, description, Instant.EPOCH, Instant.EPOCH);
        }
        public Transaction update(
            Transaction t, Long cid, TransactionType type, BigDecimal amount, LocalDate date, String description) {
            throw new UnsupportedOperationException();
        }
        public void delete(Transaction t) {
            throw new UnsupportedOperationException();
        }
    }
}
