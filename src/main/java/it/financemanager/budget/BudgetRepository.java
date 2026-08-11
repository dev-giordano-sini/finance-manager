package it.financemanager.budget;

import it.financemanager.infrastructure.persistence.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository; import java.util.List; import java.util.Optional;
public interface BudgetRepository extends JpaRepository<Budget,Long>{ List<Budget> findAllByUserIdOrderByStartDateDesc(Long userId); Optional<Budget> findByIdAndUserId(Long id,Long userId); }
