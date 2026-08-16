package it.financemanager.infrastructure.application;

import it.financemanager.category.CategoryUseCase;
import it.financemanager.common.application.*;
import it.financemanager.transaction.*;
import it.financemanager.user.CurrentUserProvider;
import java.time.LocalDate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
class TransactionalTransactionUseCase implements TransactionUseCase {
  private final TransactionService delegate;
  TransactionalTransactionUseCase(TransactionStore transactions,
                                  CategoryUseCase categories,
                                  CurrentUserProvider user) {
    delegate = new TransactionService(transactions, categories, user);
  }
  @Override
  public PageResult<TransactionResponse> list(LocalDate from, LocalDate to,
                                              PageQuery query) {
    return delegate.list(from, to, query);
  }
  @Override
  public TransactionResponse get(Long id) {
    return delegate.get(id);
  }
  @Override
  @Transactional
  public TransactionResponse create(SaveTransactionCommand command) {
    return delegate.create(command);
  }
  @Override
  @Transactional
  public TransactionResponse update(Long id, SaveTransactionCommand command) {
    return delegate.update(id, command);
  }
  @Override
  @Transactional
  public void delete(Long id) {
    delegate.delete(id);
  }
}
