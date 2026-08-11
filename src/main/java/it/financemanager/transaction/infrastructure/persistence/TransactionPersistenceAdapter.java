package it.financemanager.transaction.infrastructure.persistence;

import it.financemanager.transaction.Transaction;
import it.financemanager.transaction.port.out.TransactionOutputPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class TransactionPersistenceAdapter implements TransactionOutputPort {
    private final SpringDataTransactionRepository repository;
    public TransactionPersistenceAdapter(SpringDataTransactionRepository repository) { this.repository = repository; }
    @Override public Optional<Transaction> findByIdAndUserId(Long id, Long userId) { return repository.findByIdAndUserId(id, userId); }
    @Override public Page<Transaction> findAllByUserIdAndDateBetween(Long userId, LocalDate from, LocalDate to, Pageable pageable) { return repository.findAllByUserIdAndDateBetween(userId, from, to, pageable); }
    @Override public List<Transaction> findForDashboard(Long userId, LocalDate from, LocalDate to) { return repository.findForDashboard(userId, from, to); }
    @Override public Transaction save(Transaction transaction) { return repository.save(transaction); }
    @Override public void delete(Transaction transaction) { repository.delete(transaction); }
}
