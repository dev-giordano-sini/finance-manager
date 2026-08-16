package it.financemanager.auth;

import it.financemanager.common.exception.ConflictException;
import it.financemanager.role.BaseRole;
import it.financemanager.role.Role;
import it.financemanager.role.RoleStore;
import it.financemanager.user.User;
import it.financemanager.user.UserStore;

import java.util.Locale;

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
    public AuthResponse register(RegisterCommand command) {
        String email = command.email().trim().toLowerCase(Locale.ROOT);
        if (users.existsByEmailIgnoreCase(email)) throw new ConflictException("An account with this email already exists");
        Role user = roles.findByCode(BaseRole.ROLE_USER.getRole())
                .orElseThrow(() -> new IllegalStateException("Default USER role is not configured"));
        users.saveAndFlush(new User(email, passwordHasher.hash(command.password()), command.name().trim(), command.surname(), user));
        return token(email);
    }
    public AuthResponse login(LoginCommand command) {
        String email = command.email().trim().toLowerCase(Locale.ROOT);
        authenticator.authenticate(email, command.password());
        return token(email);
    }
    private AuthResponse token(String email) { return new AuthResponse(tokens.issue(email), "Bearer", tokens.expiresInSeconds()); }
}
