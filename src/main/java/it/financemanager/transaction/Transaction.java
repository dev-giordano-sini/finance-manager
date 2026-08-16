package it.financemanager.transaction;
import it.financemanager.category.Category;
import it.financemanager.common.BaseEntity;
import it.financemanager.user.User;
import java.math.BigDecimal;
import java.time.*;
public class Transaction extends BaseEntity {
  private final User user;
  private Category category;
  private TransactionType type;
  private BigDecimal amount;
  private LocalDate date;
  private String description;
  public Transaction(User user, Category category, TransactionType type,
                     BigDecimal amount, LocalDate date, String description) {
    this(null, 0, null, null, user, category, type, amount, date, description);
  }
  public Transaction(Long id, long version, Instant createdAt,
                     Instant updatedAt, User user, Category category,
                     TransactionType type, BigDecimal amount, LocalDate date,
                     String description) {
    super(id, version, createdAt, updatedAt);
    this.user = user;
    update(category, type, amount, date, description);
  }
  public User getUser() { return user; }
  public Category getCategory() { return category; }
  public TransactionType getType() { return type; }
  public BigDecimal getAmount() { return amount; }
  public LocalDate getDate() { return date; }
  public String getDescription() { return description; }
  public void update(Category category, TransactionType type, BigDecimal amount,
                     LocalDate date, String description) {
    this.category = category;
    this.type = type;
    this.amount = amount;
    this.date = date;
    this.description = description;
  }
}
