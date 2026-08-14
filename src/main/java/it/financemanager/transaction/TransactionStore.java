package it.financemanager.transaction;

import it.financemanager.common.application.PageQuery;
import it.financemanager.common.application.PageResult;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TransactionStore {
    Optional<Transaction> findByIdAndUserId(Long id, Long userId);
    PageResult<Transaction> findAllByUserIdAndDateBetween(Long userId, LocalDate from, LocalDate to, PageQuery pageQuery);
    List<Transaction> findForDashboard(Long userId, LocalDate from, LocalDate to);
    Transaction save(Transaction value);
    void delete(Transaction value);
}
