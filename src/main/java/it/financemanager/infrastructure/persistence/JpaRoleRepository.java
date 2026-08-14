package it.financemanager.infrastructure.persistence;

import it.financemanager.role.Role;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface JpaRoleRepository extends JpaRepository<Role, Long> {
    public Optional<Role> findByCode(String code);
}
