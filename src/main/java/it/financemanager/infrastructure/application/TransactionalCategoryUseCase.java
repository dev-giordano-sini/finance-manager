package it.financemanager.infrastructure.application;

import it.financemanager.category.*;
import it.financemanager.user.CurrentUserProvider;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
class TransactionalCategoryUseCase implements CategoryUseCase {
  private final CategoryService delegate;
  TransactionalCategoryUseCase(CategoryStore categories,
                               CurrentUserProvider user) {
    delegate = new CategoryService(categories, user);
  }
  @Override
  public List<CategoryResponse> list() {
    return delegate.list();
  }
  @Override
  public CategoryResponse get(Long id) {
    return delegate.get(id);
  }
  @Override
  @Transactional
  public CategoryResponse create(SaveCategoryCommand command) {
    return delegate.create(command);
  }
  @Override
  @Transactional
  public CategoryResponse update(Long id, SaveCategoryCommand command) {
    return delegate.update(id, command);
  }
  @Override
  @Transactional
  public void delete(Long id) {
    delegate.delete(id);
  }
  @Override
  public Category find(Long id, Long userId) {
    return delegate.find(id, userId);
  }
}
