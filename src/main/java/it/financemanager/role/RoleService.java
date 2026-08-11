package it.financemanager.role;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import it.financemanager.role.port.out.RoleOutputPort;

@Service
@Transactional
public class RoleService {
    private final RoleOutputPort roleRepository;

    public RoleService(RoleOutputPort roleRepository) {
        this.roleRepository = roleRepository;
    }

    public void save(Role role) {
        roleRepository.save(role);
    }

    public Role getUserRole(String roleCode) {
        return roleRepository.findByCode(roleCode).orElse(null);
    }
}
