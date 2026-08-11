package it.financemanager.role;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class RoleUseCaseImpl implements RoleUseCase {
    private final RoleRepository roleRepository;

    public RoleUseCaseImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public void save(Role role) {
        roleRepository.save(role);
    }

    public Role getUserRole(String roleCode) {
        return roleRepository.findByCode(roleCode).orElse(null);
    }
}
