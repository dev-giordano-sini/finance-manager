package it.financemanager.infrastructure.persistence.entity;

import jakarta.persistence.*; import java.math.BigDecimal; import java.time.LocalDate;
@Entity @Table(name="budgets", indexes=@Index(name="idx_budgets_user_period",columnList="user_id,start_date,end_date"))
public class Budget extends BaseEntity {
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="user_id",nullable=false) private User user;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="category_id",nullable=false) private Category category;
 @Column(nullable=false,precision=19,scale=2) private BigDecimal amount;
 @Column(name="start_date",nullable=false) private LocalDate startDate; @Column(name="end_date",nullable=false) private LocalDate endDate;
 protected Budget() {} public Budget(User user,Category category,BigDecimal amount,LocalDate startDate,LocalDate endDate){this.user=user;update(category,amount,startDate,endDate);}
 public Category getCategory(){return category;} public BigDecimal getAmount(){return amount;} public LocalDate getStartDate(){return startDate;} public LocalDate getEndDate(){return endDate;}
 public void update(Category category,BigDecimal amount,LocalDate startDate,LocalDate endDate){this.category=category;this.amount=amount;this.startDate=startDate;this.endDate=endDate;}
}
