package it.financemanager.infrastructure.persistence.entity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
@Entity
@Table(name = "budgets")
public class BudgetEntity extends JpaBaseEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "user_id") public UserEntity user;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id")
    public CategoryEntity category;
    @Column(nullable = false, precision = 19, scale = 2) public BigDecimal amount;
    @Column(name = "start_date") public LocalDate startDate;
    @Column(name = "end_date") public LocalDate endDate;
    protected BudgetEntity() {}
    public BudgetEntity(UserEntity u, CategoryEntity c, BigDecimal a, LocalDate s, LocalDate e) {
        user = u;
        set(c, a, s, e);
    }
    public void set(CategoryEntity c, BigDecimal a, LocalDate s, LocalDate e) {
        category = c;
        amount = a;
        startDate = s;
        endDate = e;
    }
}
