package it.financemanager.infrastructure.persistence;

import it.financemanager.infrastructure.persistence.entity.BudgetJpaEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
interface JpaBudgetRepository extends JpaRepository<BudgetJpaEntity, Long> {
  @EntityGraph(attributePaths = {"user", "user.role", "category",
                                 "category.user", "category.user.role"})
  List<BudgetJpaEntity>
  findAllByUserIdOrderByStartDateDesc(Long userId);
  @EntityGraph(attributePaths = {"user", "user.role", "category",
                                 "category.user", "category.user.role"})
  Optional<BudgetJpaEntity>
  findByIdAndUserId(Long id, Long userId);
}
