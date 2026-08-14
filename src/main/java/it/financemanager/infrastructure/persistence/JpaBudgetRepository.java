package it.financemanager.infrastructure.persistence;

import it.financemanager.budget.Budget;
import org.springframework.data.jpa.repository.JpaRepository; import java.util.List; import java.util.Optional;
interface JpaBudgetRepository extends JpaRepository<Budget,Long>{ List<Budget> findAllByUserIdOrderByStartDateDesc(Long userId); Optional<Budget> findByIdAndUserId(Long id,Long userId); }
