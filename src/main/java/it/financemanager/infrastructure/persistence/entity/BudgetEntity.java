package it.financemanager.infrastructure.persistence.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "budgets",
        indexes = @Index(name = "idx_budgets_user_period",
                columnList = "user_id,start_date,end_date"))
public class BudgetEntity extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    public UserEntity user;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    public CategoryEntity category;
    @Column(nullable = false, precision = 19, scale = 2)
    public BigDecimal amount;
    @Column(name = "start_date", nullable = false)
    public LocalDate startDate;
    @Column(name = "end_date", nullable = false)
    public LocalDate endDate;

    public BudgetEntity() {
    }
}
