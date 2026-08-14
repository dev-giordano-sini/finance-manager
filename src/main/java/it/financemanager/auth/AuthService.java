package it.financemanager.auth;

import it.financemanager.common.exception.ConflictException;
import it.financemanager.role.BaseRole;
import it.financemanager.role.Role;
import it.financemanager.role.RoleStore;
import it.financemanager.user.User;
import it.financemanager.user.UserStore;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
public class AuthService implements AuthUseCase {
    private final UserStore users;
    private final PasswordHasher passwordHasher;
    private final CredentialAuthenticator authenticator;
    private final AccessTokenIssuer tokens;
    private final RoleStore roles;
    public AuthService(UserStore users, PasswordHasher passwordHasher, CredentialAuthenticator authenticator, AccessTokenIssuer tokens, RoleStore roles) {
        this.users = users; this.passwordHasher = passwordHasher; this.authenticator = authenticator; this.tokens = tokens;
        this.roles = roles;
    }
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        String email = request.email().trim().toLowerCase(Locale.ROOT);
        if (users.existsByEmailIgnoreCase(email)) throw new ConflictException("An account with this email already exists");
        Role user = roles.findByCode(BaseRole.ROLE_USER.getRole())
                .orElseThrow(() -> new IllegalStateException("Default USER role is not configured"));
        users.saveAndFlush(new User(email, passwordHasher.hash(request.password()), request.name().trim(), request.surname(), user));
        return token(email);
    }
    public AuthResponse login(LoginRequest request) {
        String email = request.email().trim().toLowerCase(Locale.ROOT);
        authenticator.authenticate(email, request.password());
        return token(email);
    }
    private AuthResponse token(String email) { return new AuthResponse(tokens.issue(email), "Bearer", tokens.expiresInSeconds()); }
}
