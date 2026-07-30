package it.financemanager.auth;

import it.financemanager.common.exception.ConflictException;
import it.financemanager.common.security.JwtService;
import it.financemanager.user.Role;
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
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwt;
    public AuthService(UserRepository users, PasswordEncoder encoder, AuthenticationManager authenticationManager, JwtService jwt) {
        this.users = users; this.encoder = encoder; this.authenticationManager = authenticationManager; this.jwt = jwt;
    }
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        String email = request.email().trim().toLowerCase(Locale.ROOT);
        if (users.existsByEmailIgnoreCase(email)) throw new ConflictException("An account with this email already exists");
        try {
            users.saveAndFlush(new User(email, encoder.encode(request.password()), request.name().trim(), Role.USER));
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
