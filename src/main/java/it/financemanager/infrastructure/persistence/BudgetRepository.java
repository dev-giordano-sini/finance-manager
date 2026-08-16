package it.financemanager.infrastructure.persistence;

import it.financemanager.infrastructure.persistence.entity.BudgetEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

interface BudgetRepository extends JpaRepository<BudgetEntity, Long> {
    @EntityGraph(attributePaths = {"user", "user.role", "category",
            "category.user", "category.user.role"})
    List<BudgetEntity>
    findAllByUserIdOrderByStartDateDesc(Long userId);

    @EntityGraph(attributePaths = {"user", "user.role", "category",
            "category.user", "category.user.role"})
    Optional<BudgetEntity>
    findByIdAndUserId(Long id, Long userId);
}
