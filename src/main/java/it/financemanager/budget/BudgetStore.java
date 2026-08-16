package it.financemanager.budget;

import java.util.List;
import java.util.Optional;

public interface BudgetStore {
    List<Budget> findAllByUserIdOrderByStartDateDesc(Long userId);

    Optional<Budget> findByIdAndUserId(Long id, Long userId);

    Budget save(Budget value);

    void delete(Budget value);
}
