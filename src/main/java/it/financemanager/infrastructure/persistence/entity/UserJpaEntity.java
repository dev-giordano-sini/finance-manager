package it.financemanager.infrastructure.persistence.entity;
import jakarta.persistence.*;
@Entity
@Table(name = "users")
public class UserJpaEntity extends JpaBaseEntity {
  @Column(nullable = false, unique = true, length = 254) public String email;
  @Column(nullable = false) public String password;
  @Column(nullable = false, length = 100) public String name;
  @Column(nullable = false, length = 100) public String surname;
  @OneToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "role_id", nullable = false)
  public RoleJpaEntity role;
  public UserJpaEntity() {}
  public UserJpaEntity(String email, String password, String name,
                       String surname, RoleJpaEntity role) {
    this.email = email;
    this.password = password;
    this.name = name;
    this.surname = surname;
    this.role = role;
  }
}
