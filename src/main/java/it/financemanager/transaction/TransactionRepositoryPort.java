package it.financemanager.transaction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TransactionRepositoryPort {
    Optional<Transaction> findByIdAndUserId(Long id, Long userId);
    Page<Transaction> findAllByUserIdAndDateBetween(Long userId, LocalDate from, LocalDate to, Pageable pageable);
    List<Transaction> findForDashboard(Long userId, LocalDate from, LocalDate to);
    <S extends Transaction> S save(S transaction);
    void delete(Transaction transaction);
}
