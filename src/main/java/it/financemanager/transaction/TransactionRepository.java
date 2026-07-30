package it.financemanager.transaction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<Transaction> findByIdAndUserId(Long id, Long userId);
    Page<Transaction> findAllByUserIdAndDateBetween(Long userId, LocalDate from, LocalDate to, Pageable pageable);
}
