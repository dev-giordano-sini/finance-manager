package it.financemanager.transaction;

import it.financemanager.category.Category;
import it.financemanager.common.BaseEntity;
import it.financemanager.user.User;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity @Table(name = "transactions", indexes = @Index(name = "idx_transactions_user_date", columnList = "user_id,transaction_date"))
public class Transaction extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "user_id", nullable = false) private User user;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "category_id", nullable = false) private Category category;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 10) private TransactionType type;
    @Column(nullable = false, precision = 19, scale = 2) private BigDecimal amount;
    @Column(nullable = false, name = "transaction_date") private LocalDate date;
    @Column(length = 500) private String description;
    protected Transaction() { }
    public Transaction(User user, Category category, TransactionType type, BigDecimal amount, LocalDate date, String description) { this.user=user; update(category,type,amount,date,description); }
    public Category getCategory() { return category; } public TransactionType getType() { return type; } public BigDecimal getAmount() { return amount; }
    public LocalDate getDate() { return date; } public String getDescription() { return description; }
    public void update(Category category, TransactionType type, BigDecimal amount, LocalDate date, String description) { this.category=category; this.type=type; this.amount=amount; this.date=date; this.description=description; }
}
