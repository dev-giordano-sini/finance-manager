package it.financemanager.infrastructure.persistence;

import it.financemanager.budget.Budget;
import it.financemanager.category.Category;
import it.financemanager.infrastructure.persistence.entity.*;
import it.financemanager.role.Role;
import it.financemanager.transaction.Transaction;
import it.financemanager.user.User;

final class DomainPersistenceMapper {
    private DomainPersistenceMapper() {
    }

    static Role role(RoleEntity e) {
        return new Role(e.getId(), e.getVersion(), e.getCreatedAt(),
                e.getUpdatedAt(), e.code, e.description);
    }

    static User user(UserEntity e) {
        return new User(e.getId(), e.getVersion(), e.getCreatedAt(),
                e.getUpdatedAt(), e.email, e.password, e.name, e.surname,
                role(e.role));
    }

    static Category category(CategoryEntity e) {
        return new Category(e.getId(), e.getVersion(), e.getCreatedAt(),
                e.getUpdatedAt(), user(e.user), e.name, e.color);
    }

    static Budget budget(BudgetEntity e) {
        return new Budget(e.getId(), e.getVersion(), e.getCreatedAt(),
                e.getUpdatedAt(), user(e.user), category(e.category),
                e.amount, e.startDate, e.endDate);
    }

    static Transaction transaction(TransactionEntity e) {
        return new Transaction(e.getId(), e.getVersion(), e.getCreatedAt(),
                e.getUpdatedAt(), user(e.user), category(e.category),
                e.type, e.amount, e.date, e.description);
    }
}
