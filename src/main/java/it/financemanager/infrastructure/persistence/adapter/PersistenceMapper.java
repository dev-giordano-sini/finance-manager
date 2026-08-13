package it.financemanager.infrastructure.persistence.adapter;
import it.financemanager.domain.model.*;
import it.financemanager.infrastructure.persistence.entity.*;
final class PersistenceMapper {
    static User user(UserEntity e) {
        return new User(e.id, e.email, e.password, e.name, e.surname, e.role.code, e.createdAt, e.updatedAt);
    }
    static Category category(CategoryEntity e) {
        return new Category(e.id, e.user.id, e.name, e.color, e.createdAt, e.updatedAt);
    }
    static Transaction transaction(TransactionEntity e) {
        return new Transaction(e.id, e.user.id, e.category.id, e.category.name, e.category.color, e.type, e.amount,
            e.date, e.description, e.createdAt, e.updatedAt);
    }
    static Budget budget(BudgetEntity e) {
        return new Budget(e.id, e.user.id, e.category.id, e.category.name, e.amount, e.startDate, e.endDate,
            e.createdAt, e.updatedAt);
    }
}
