package it.financemanager.infrastructure.application;

import it.financemanager.budget.*;
import it.financemanager.category.CategoryUseCase;
import it.financemanager.user.CurrentUserProvider;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
class TransactionalBudgetUseCase implements BudgetUseCase {
  private final BudgetService delegate;
  TransactionalBudgetUseCase(BudgetStore budgets, CategoryUseCase categories,
                             CurrentUserProvider user) {
    delegate = new BudgetService(budgets, categories, user);
  }
  @Override
  public List<BudgetResponse> list() {
    return delegate.list();
  }
  @Override
  public BudgetResponse get(Long id) {
    return delegate.get(id);
  }
  @Override
  @Transactional
  public BudgetResponse create(SaveBudgetCommand command) {
    return delegate.create(command);
  }
  @Override
  @Transactional
  public BudgetResponse update(Long id, SaveBudgetCommand command) {
    return delegate.update(id, command);
  }
  @Override
  @Transactional
  public void delete(Long id) {
    delegate.delete(id);
  }
}
