package it.financemanager.budget;

import java.util.List;
import java.util.Optional;

public interface BudgetRepositoryPort {
    List<Budget> findAllByUserIdOrderByStartDateDesc(Long userId);
    Optional<Budget> findByIdAndUserId(Long id, Long userId);
    <S extends Budget> S save(S budget);
    void delete(Budget budget);
}
