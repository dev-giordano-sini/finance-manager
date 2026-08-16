package it.financemanager.infrastructure.persistence.entity;
import jakarta.persistence.*;
import java.time.Instant;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class JpaBaseEntity {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) protected Long id;
  @Version protected long version;
  @CreatedDate
  @Column(nullable = false, updatable = false)
  protected Instant createdAt;
  @LastModifiedDate @Column(nullable = false) protected Instant updatedAt;
  public Long getId() { return id; }
  public long getVersion() { return version; }
  public Instant getCreatedAt() { return createdAt; }
  public Instant getUpdatedAt() { return updatedAt; }
}
