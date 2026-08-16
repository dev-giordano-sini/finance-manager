package it.financemanager.infrastructure.persistence;
import it.financemanager.common.application.*;
import it.financemanager.infrastructure.persistence.entity.*;
import it.financemanager.transaction.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.*;
@Repository
class TransactionPersistenceAdapter implements TransactionStore {
  private final JpaTransactionRepository repository;
  private final JpaUserRepository users;
  private final JpaCategoryRepository categories;
  TransactionPersistenceAdapter(JpaTransactionRepository r, JpaUserRepository u,
                                JpaCategoryRepository c) {
    repository = r;
    users = u;
    categories = c;
  }
  public Optional<Transaction> findByIdAndUserId(Long id, Long uid) {
    return repository.findByIdAndUserId(id, uid).map(
        DomainPersistenceMapper::transaction);
  }
  public PageResult<Transaction> findAllByUserIdAndDateBetween(Long uid,
                                                               LocalDate from,
                                                               LocalDate to,
                                                               PageQuery q) {
    Sort.Direction d = q.direction() == PageQuery.Direction.ASC
                           ? Sort.Direction.ASC
                           : Sort.Direction.DESC;
    var p = repository.findAllByUserIdAndDateBetween(
        uid, from, to,
        PageRequest.of(q.page(), q.size(), Sort.by(d, q.sortBy())));
    return new PageResult<>(p.getContent()
                                .stream()
                                .map(DomainPersistenceMapper::transaction)
                                .toList(),
                            p.getNumber(), p.getSize(), p.getTotalElements(),
                            p.getTotalPages());
  }
  public List<Transaction> findForDashboard(Long uid, LocalDate from,
                                            LocalDate to) {
    return repository.findForDashboard(uid, from, to)
        .stream()
        .map(DomainPersistenceMapper::transaction)
        .toList();
  }
  public Transaction save(Transaction v) {
    TransactionJpaEntity e = v.getId() == null
                                 ? new TransactionJpaEntity()
                                 : repository.findById(v.getId()).orElseThrow();
    e.user = users.getReferenceById(v.getUser().getId());
    e.category = categories.getReferenceById(v.getCategory().getId());
    e.type = v.getType();
    e.amount = v.getAmount();
    e.date = v.getDate();
    e.description = v.getDescription();
    return DomainPersistenceMapper.transaction(repository.save(e));
  }
  public void delete(Transaction v) { repository.deleteById(v.getId()); }
}
