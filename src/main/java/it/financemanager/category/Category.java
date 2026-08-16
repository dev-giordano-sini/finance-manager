package it.financemanager.category;
import it.financemanager.common.BaseEntity;
import it.financemanager.user.User;
import java.time.Instant;
public class Category extends BaseEntity {
  private final User user;
  private String name, color;
  public Category(User user, String name, String color) {
    this(null, 0, null, null, user, name, color);
  }
  public Category(Long id, long version, Instant createdAt, Instant updatedAt,
                  User user, String name, String color) {
    super(id, version, createdAt, updatedAt);
    this.user = user;
    this.name = name;
    this.color = color;
  }
  public User getUser() { return user; }
  public String getName() { return name; }
  public String getColor() { return color; }
  public void update(String name, String color) {
    this.name = name;
    this.color = color;
  }
}
