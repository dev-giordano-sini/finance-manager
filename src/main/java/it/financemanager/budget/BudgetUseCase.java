package it.financemanager.budget;

import java.util.List;

public interface BudgetUseCase {
    List<BudgetResponse> list();

    BudgetResponse get(Long id);

    BudgetResponse create(BudgetRequest request);

    BudgetResponse update(Long id, BudgetRequest request);

    void delete(Long id);
}
