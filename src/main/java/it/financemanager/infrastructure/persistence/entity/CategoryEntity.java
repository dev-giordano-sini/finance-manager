package it.financemanager.infrastructure.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "categories",
        uniqueConstraints = @UniqueConstraint(name = "uk_categories_user_name",
                columnNames = {"user_id", "name"}))
public class CategoryEntity extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    public UserEntity user;
    @Column(nullable = false, length = 80)
    public String name;
    @Column(nullable = false, length = 7)
    public String color;

    public CategoryEntity() {
    }

    public CategoryEntity(UserEntity user, String name, String color) {
        this.user = user;
        this.name = name;
        this.color = color;
    }
}
