package it.financemanager.infrastructure.persistence;

import it.financemanager.budget.Budget;
import it.financemanager.budget.BudgetStore;
import it.financemanager.infrastructure.persistence.entity.BudgetEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
class BudgetPersistenceAdapter implements BudgetStore {
    private final BudgetRepository repository;
    private final UserRepository users;
    private final CategoryRepository categories;

    BudgetPersistenceAdapter(BudgetRepository r, UserRepository u,
                             CategoryRepository c) {
        repository = r;
        users = u;
        categories = c;
    }

    public List<Budget> findAllByUserIdOrderByStartDateDesc(Long id) {
        return repository.findAllByUserIdOrderByStartDateDesc(id)
                .stream()
                .map(DomainPersistenceMapper::budget)
                .toList();
    }

    public Optional<Budget> findByIdAndUserId(Long id, Long userId) {
        return repository.findByIdAndUserId(id, userId)
                .map(DomainPersistenceMapper::budget);
    }

    public Budget save(Budget v) {
        BudgetEntity e = v.getId() == null
                ? new BudgetEntity()
                : repository.findById(v.getId()).orElseThrow();
        e.user = users.getReferenceById(v.getUser().getId());
        e.category = categories.getReferenceById(v.getCategory().getId());
        e.amount = v.getAmount();
        e.startDate = v.getStartDate();
        e.endDate = v.getEndDate();
        return DomainPersistenceMapper.budget(repository.save(e));
    }

    public void delete(Budget v) {
        repository.deleteById(v.getId());
    }
}
