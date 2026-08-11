package it.financemanager.infrastructure.security;

import it.financemanager.role.BaseRole;
import it.financemanager.role.Role;
import it.financemanager.role.RoleRepository;
import it.financemanager.user.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final UserRepository repository;
    private final Role userRole;

    public UserDetailsService(UserRepository repository, RoleRepository roleRepository) {
        this.repository = repository;
        this.userRole = roleRepository.findByCode(BaseRole.ROLE_USER.getRole())
                .orElse(new Role(BaseRole.ROLE_USER.getRole(), ""));
    }

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String email) {
        return repository.findByEmailIgnoreCase(email)
                .map(user -> User.withUsername(user.getEmail())
                        .password(user.getPassword())
                        .roles(userRole.getCode())
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("Invalid credentials"));
    }
}
