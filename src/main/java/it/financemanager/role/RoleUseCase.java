package it.financemanager.role;

public interface RoleUseCase {
    void save(Role role);
    Role getUserRole(String roleCode);
}
