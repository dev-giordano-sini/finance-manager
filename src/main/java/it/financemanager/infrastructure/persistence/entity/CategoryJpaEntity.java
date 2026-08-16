package it.financemanager.infrastructure.persistence.entity;
import jakarta.persistence.*;
@Entity
@Table(name = "categories",
       uniqueConstraints = @UniqueConstraint(name = "uk_categories_user_name",
                                             columnNames = {"user_id", "name"}))
public class CategoryJpaEntity extends JpaBaseEntity {
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", nullable = false)
  public UserJpaEntity user;
  @Column(nullable = false, length = 80) public String name;
  @Column(nullable = false, length = 7) public String color;
  public CategoryJpaEntity() {}
  public CategoryJpaEntity(UserJpaEntity user, String name, String color) {
    this.user = user;
    this.name = name;
    this.color = color;
  }
}
