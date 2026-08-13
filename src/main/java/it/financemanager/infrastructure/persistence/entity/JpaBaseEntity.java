package it.financemanager.infrastructure.persistence.entity;
import jakarta.persistence.*;
import java.time.Instant;
import org.springframework.data.annotation.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class JpaBaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) public Long id;
    @Version public long version;
    @CreatedDate @Column(nullable = false, updatable = false) public Instant createdAt;
    @LastModifiedDate @Column(nullable = false) public Instant updatedAt;
}
