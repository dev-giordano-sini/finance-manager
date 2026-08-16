package it.financemanager.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles")
public class RoleEntity extends BaseEntity {
    @Column(name = "code", nullable = false, unique = true, length = 50)
    public String code;
    @Column(name = "description")
    public String description;

    public RoleEntity() {
    }

    public RoleEntity(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
