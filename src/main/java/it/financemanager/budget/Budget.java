package it.financemanager.budget;
import it.financemanager.category.Category;
import it.financemanager.common.BaseEntity;
import it.financemanager.user.User;
import java.math.BigDecimal;
import java.time.*;
public class Budget extends BaseEntity {
  private final User user;
  private Category category;
  private BigDecimal amount;
  private LocalDate startDate, endDate;
  public Budget(User user, Category category, BigDecimal amount,
                LocalDate startDate, LocalDate endDate) {
    this(null, 0, null, null, user, category, amount, startDate, endDate);
  }
  public Budget(Long id, long version, Instant createdAt, Instant updatedAt,
                User user, Category category, BigDecimal amount,
                LocalDate startDate, LocalDate endDate) {
    super(id, version, createdAt, updatedAt);
    this.user = user;
    update(category, amount, startDate, endDate);
  }
  public User getUser() { return user; }
  public Category getCategory() { return category; }
  public BigDecimal getAmount() { return amount; }
  public LocalDate getStartDate() { return startDate; }
  public LocalDate getEndDate() { return endDate; }
  public void update(Category category, BigDecimal amount, LocalDate startDate,
                     LocalDate endDate) {
    this.category = category;
    this.amount = amount;
    this.startDate = startDate;
    this.endDate = endDate;
  }
}
