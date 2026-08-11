package it.financemanager.role;

import it.financemanager.infrastructure.persistence.entity.Role;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    public Optional<Role> findByCode(String code);
}
