package it.financemanager.infrastructure.persistence.entity;
import it.financemanager.domain.model.TransactionType;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
@Entity
@Table(name = "transactions")
public class TransactionEntity extends JpaBaseEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "user_id") public UserEntity user;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id")
    public CategoryEntity category;
    @Enumerated(EnumType.STRING) @Column(nullable = false) public TransactionType type;
    @Column(nullable = false, precision = 19, scale = 2) public BigDecimal amount;
    @Column(name = "transaction_date", nullable = false) public LocalDate date;
    @Column(length = 500) public String description;
    protected TransactionEntity() {}
    public TransactionEntity(UserEntity u, CategoryEntity c, TransactionType t, BigDecimal a, LocalDate d, String x) {
        user = u;
        category = c;
        set(c, t, a, d, x);
    }
    public void set(CategoryEntity c, TransactionType t, BigDecimal a, LocalDate d, String x) {
        category = c;
        type = t;
        amount = a;
        date = d;
        description = x;
    }
}
