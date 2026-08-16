package it.financemanager.infrastructure.persistence;
import it.financemanager.budget.Budget;
import it.financemanager.category.Category;
import it.financemanager.infrastructure.persistence.entity.*;
import it.financemanager.role.Role;
import it.financemanager.transaction.Transaction;
import it.financemanager.user.User;
final class DomainPersistenceMapper {
  private DomainPersistenceMapper() {}
  static Role role(RoleJpaEntity e) {
    return new Role(e.getId(), e.getVersion(), e.getCreatedAt(),
                    e.getUpdatedAt(), e.code, e.description);
  }
  static User user(UserJpaEntity e) {
    return new User(e.getId(), e.getVersion(), e.getCreatedAt(),
                    e.getUpdatedAt(), e.email, e.password, e.name, e.surname,
                    role(e.role));
  }
  static Category category(CategoryJpaEntity e) {
    return new Category(e.getId(), e.getVersion(), e.getCreatedAt(),
                        e.getUpdatedAt(), user(e.user), e.name, e.color);
  }
  static Budget budget(BudgetJpaEntity e) {
    return new Budget(e.getId(), e.getVersion(), e.getCreatedAt(),
                      e.getUpdatedAt(), user(e.user), category(e.category),
                      e.amount, e.startDate, e.endDate);
  }
  static Transaction transaction(TransactionJpaEntity e) {
    return new Transaction(e.getId(), e.getVersion(), e.getCreatedAt(),
                           e.getUpdatedAt(), user(e.user), category(e.category),
                           e.type, e.amount, e.date, e.description);
  }
}
