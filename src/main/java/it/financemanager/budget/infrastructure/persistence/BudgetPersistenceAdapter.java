package it.financemanager.budget.infrastructure.persistence;
import it.financemanager.budget.Budget;
import it.financemanager.budget.port.out.BudgetOutputPort;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
@Repository
public class BudgetPersistenceAdapter implements BudgetOutputPort {
    private final SpringDataBudgetRepository repository;
    public BudgetPersistenceAdapter(SpringDataBudgetRepository repository) { this.repository = repository; }
    @Override public List<Budget> findAllByUserIdOrderByStartDateDesc(Long userId) { return repository.findAllByUserIdOrderByStartDateDesc(userId); }
    @Override public Optional<Budget> findByIdAndUserId(Long id, Long userId) { return repository.findByIdAndUserId(id, userId); }
    @Override public Budget save(Budget budget) { return repository.save(budget); }
    @Override public void delete(Budget budget) { repository.delete(budget); }
}
