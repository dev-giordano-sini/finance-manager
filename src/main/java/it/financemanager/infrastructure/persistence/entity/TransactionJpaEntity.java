package it.financemanager.infrastructure.persistence.entity;
import it.financemanager.transaction.TransactionType;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
@Entity(name = "TransactionJpaEntity")
@Table(name = "transactions",
       indexes = @Index(name = "idx_transactions_user_date",
                        columnList = "user_id,transaction_date"))
public class TransactionJpaEntity extends JpaBaseEntity {
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", nullable = false)
  public UserJpaEntity user;
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "category_id", nullable = false)
  public CategoryJpaEntity category;
  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 10)
  public TransactionType type;
  @Column(nullable = false, precision = 19, scale = 2) public BigDecimal amount;
  @Column(nullable = false, name = "transaction_date") public LocalDate date;
  @Column(length = 500) public String description;
  public TransactionJpaEntity() {}
}
