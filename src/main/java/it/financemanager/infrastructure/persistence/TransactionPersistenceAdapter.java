package it.financemanager.infrastructure.persistence;

import it.financemanager.common.application.PageQuery;
import it.financemanager.common.application.PageResult;
import it.financemanager.transaction.Transaction;
import it.financemanager.transaction.TransactionStore;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
class TransactionPersistenceAdapter implements TransactionStore {
    private final JpaTransactionRepository repository;

    TransactionPersistenceAdapter(JpaTransactionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Transaction> findByIdAndUserId(Long id, Long userId) {
        return repository.findByIdAndUserId(id, userId);
    }

    @Override
    public PageResult<Transaction> findAllByUserIdAndDateBetween(Long userId, LocalDate from, LocalDate to, PageQuery query) {
        Sort.Direction direction = query.direction() == PageQuery.Direction.ASC ? Sort.Direction.ASC : Sort.Direction.DESC;
        var page = repository.findAllByUserIdAndDateBetween(userId, from, to,
                PageRequest.of(query.page(), query.size(), Sort.by(direction, query.sortBy())));
        return new PageResult<>(page.getContent(), page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages());
    }

    @Override public List<Transaction> findForDashboard(Long userId, LocalDate from, LocalDate to) { return repository.findForDashboard(userId, from, to); }
    @Override public Transaction save(Transaction value) { return repository.save(value); }
    @Override public void delete(Transaction value) { repository.delete(value); }
}
