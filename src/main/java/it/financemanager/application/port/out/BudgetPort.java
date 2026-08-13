package it.financemanager.application.port.out;
import it.financemanager.domain.model.Budget;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
public interface BudgetPort {
    List<Budget> findAllByUser(Long userId);
    Optional<Budget> findByIdAndUser(Long id, Long userId);
    Budget create(Long userId, Long categoryId, BigDecimal amount, LocalDate start, LocalDate end);
    Budget update(Budget budget, Long categoryId, BigDecimal amount, LocalDate start, LocalDate end);
    void delete(Budget budget);
}
