package it.financemanager.infrastructure.persistence.entity;
import jakarta.persistence.*;
@Entity
@Table(name = "categories")
public class CategoryEntity extends JpaBaseEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "user_id") public UserEntity user;
    @Column(nullable = false, length = 80) public String name;
    @Column(nullable = false, length = 7) public String color;
    protected CategoryEntity() {}
    public CategoryEntity(UserEntity u, String n, String c) {
        user = u;
        name = n;
        color = c;
    }
}
