package it.financemanager.user;

import it.financemanager.common.BaseEntity;
import it.financemanager.role.Role;

import java.time.Instant;

public class User extends BaseEntity {
    private final String email, password, name, surname;
    private final Role role;

    public User(String email, String password, String name, String surname,
                Role role) {
        this(null, 0, null, null, email, password, name, surname, role);
    }

    public User(Long id, long version, Instant createdAt, Instant updatedAt,
                String email, String password, String name, String surname,
                Role role) {
        super(id, version, createdAt, updatedAt);
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Role getRole() {
        return role;
    }
}
