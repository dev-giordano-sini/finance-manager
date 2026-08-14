package it.financemanager.role;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class RoleService {
    private final RoleStore roleRepository;

    public RoleService(RoleStore roleRepository) {
        this.roleRepository = roleRepository;
    }

    public void save(Role role) {
        roleRepository.save(role);
    }

    public Role getUserRole(String roleCode) {
        return roleRepository.findByCode(roleCode).orElse(null);
    }
}
