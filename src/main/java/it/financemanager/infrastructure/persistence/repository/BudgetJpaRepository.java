package it.financemanager.infrastructure.persistence.repository;
import it.financemanager.infrastructure.persistence.entity.BudgetEntity;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
public interface BudgetJpaRepository extends JpaRepository<BudgetEntity, Long> {
    List<BudgetEntity> findAllByUserIdOrderByStartDateDesc(Long uid);
    Optional<BudgetEntity> findByIdAndUserId(Long id, Long uid);
}
