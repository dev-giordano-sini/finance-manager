package it.financemanager.application.port.out;
import it.financemanager.application.model.PageResult;
import it.financemanager.domain.model.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
public interface TransactionPort {
    List<Transaction> findForDashboard(Long userId, LocalDate from, LocalDate to);
    PageResult<Transaction> findAll(Long userId, LocalDate from, LocalDate to, int page, int size);
    Optional<Transaction> findByIdAndUser(Long id, Long userId);
    Transaction create(
        Long userId, Long categoryId, TransactionType type, BigDecimal amount, LocalDate date, String description);
    Transaction update(Transaction transaction, Long categoryId, TransactionType type, BigDecimal amount,
        LocalDate date, String description);
    void delete(Transaction transaction);
}
