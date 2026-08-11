package it.financemanager.transaction.port.out;

import it.financemanager.transaction.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TransactionOutputPort {
    Optional<Transaction> findByIdAndUserId(Long id, Long userId);
    Page<Transaction> findAllByUserIdAndDateBetween(Long userId, LocalDate from, LocalDate to, Pageable pageable);
    List<Transaction> findForDashboard(Long userId, LocalDate from, LocalDate to);
    Transaction save(Transaction transaction);
    void delete(Transaction transaction);
}
