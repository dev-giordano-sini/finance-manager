package it.financemanager.application.port.in;
import it.financemanager.domain.model.Budget; import java.math.BigDecimal; import java.time.LocalDate; import java.util.List;
public interface BudgetUseCase { List<Budget> list(); Budget get(Long id); Budget create(Long categoryId,BigDecimal amount,LocalDate start,LocalDate end); Budget update(Long id,Long categoryId,BigDecimal amount,LocalDate start,LocalDate end); void delete(Long id); }
