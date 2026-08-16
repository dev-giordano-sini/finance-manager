package it.financemanager.infrastructure.persistence;
import it.financemanager.budget.*;
import it.financemanager.infrastructure.persistence.entity.*;
import org.springframework.stereotype.Repository;
import java.util.*;
@Repository
class BudgetPersistenceAdapter implements BudgetStore {
  private final JpaBudgetRepository repository;
  private final JpaUserRepository users;
  private final JpaCategoryRepository categories;
  BudgetPersistenceAdapter(JpaBudgetRepository r, JpaUserRepository u,
                           JpaCategoryRepository c) {
    repository = r;
    users = u;
    categories = c;
  }
  public List<Budget> findAllByUserIdOrderByStartDateDesc(Long id) {
    return repository.findAllByUserIdOrderByStartDateDesc(id)
        .stream()
        .map(DomainPersistenceMapper::budget)
        .toList();
  }
  public Optional<Budget> findByIdAndUserId(Long id, Long userId) {
    return repository.findByIdAndUserId(id, userId)
        .map(DomainPersistenceMapper::budget);
  }
  public Budget save(Budget v) {
    BudgetJpaEntity e = v.getId() == null
                            ? new BudgetJpaEntity()
                            : repository.findById(v.getId()).orElseThrow();
    e.user = users.getReferenceById(v.getUser().getId());
    e.category = categories.getReferenceById(v.getCategory().getId());
    e.amount = v.getAmount();
    e.startDate = v.getStartDate();
    e.endDate = v.getEndDate();
    return DomainPersistenceMapper.budget(repository.save(e));
  }
  public void delete(Budget v) { repository.deleteById(v.getId()); }
}
