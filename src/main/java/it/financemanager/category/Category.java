package it.financemanager.category;

import it.financemanager.common.BaseEntity;
import it.financemanager.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "categories", uniqueConstraints = @UniqueConstraint(name = "uk_categories_user_name", columnNames = {"user_id", "name"}))
public class Category extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(nullable = false, length = 80) private String name;
    @Column(nullable = false, length = 7) private String color;
    protected Category() { }
    public Category(User user, String name, String color) { this.user = user; this.name = name; this.color = color; }
    public String getName() { return name; }
    public String getColor() { return color; }
    public void update(String name, String color) { this.name = name; this.color = color; }
}
