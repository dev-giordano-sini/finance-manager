package it.financemanager.auth;

import it.financemanager.common.exception.ConflictException;
import it.financemanager.infrastructure.security.JwtService;
import it.financemanager.role.BaseRole;
import it.financemanager.role.Role;
import it.financemanager.role.RoleService;
import it.financemanager.user.User;
import it.financemanager.user.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
public class AuthService {
    private final UserRepository users;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwt;
    private final RoleService roleService;
    public AuthService(UserRepository users, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwt, RoleService roleService) {
        this.users = users; this.passwordEncoder = passwordEncoder; this.authenticationManager = authenticationManager; this.jwt = jwt;
        this.roleService = roleService;
    }
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        String email = request.email().trim().toLowerCase(Locale.ROOT);
        if (users.existsByEmailIgnoreCase(email)) throw new ConflictException("An account with this email already exists");
        try {
            Role user = roleService.getUserRole(BaseRole.ROLE_USER.getRole());
            users.saveAndFlush(new User(email, passwordEncoder.encode(request.password()), request.name().trim(), request.surname(), user));
        } catch (DataIntegrityViolationException ex) {
            throw new ConflictException("An account with this email already exists");
        }
        return token(email);
    }
    public AuthResponse login(LoginRequest request) {
        String email = request.email().trim().toLowerCase(Locale.ROOT);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, request.password()));
        return token(email);
    }
    private AuthResponse token(String email) { return new AuthResponse(jwt.generate(email), "Bearer", jwt.expiresInSeconds()); }
}
