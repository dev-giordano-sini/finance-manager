package it.financemanager.infrastructure.persistence.entity;
import jakarta.persistence.*;
@Entity
@Table(name = "roles")
public class RoleJpaEntity extends JpaBaseEntity {
  @Column(name = "code", nullable = false, unique = true, length = 50)
  public String code;
  @Column(name = "description") public String description;
  public RoleJpaEntity() {}
  public RoleJpaEntity(String code, String description) {
    this.code = code;
    this.description = description;
  }
}
