package it.financemanager.budget.port.out;
import it.financemanager.budget.Budget;
import java.util.List;
import java.util.Optional;
public interface BudgetOutputPort {
    List<Budget> findAllByUserIdOrderByStartDateDesc(Long userId);
    Optional<Budget> findByIdAndUserId(Long id, Long userId);
    Budget save(Budget budget);
    void delete(Budget budget);
}
