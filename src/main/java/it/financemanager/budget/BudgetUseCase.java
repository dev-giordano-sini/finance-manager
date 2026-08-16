package it.financemanager.budget;
import java.util.List;
public interface BudgetUseCase { List<BudgetResponse> list(); BudgetResponse get(Long id); BudgetResponse create(SaveBudgetCommand command); BudgetResponse update(Long id, SaveBudgetCommand command); void delete(Long id); }
