package it.financemanager.auth;

import it.financemanager.common.exception.ConflictException;
import it.financemanager.common.security.JwtService;
import it.financemanager.user.User;
import it.financemanager.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @Mock private UserRepository users;
    @Mock private PasswordEncoder encoder;
    @Mock private AuthenticationManager authenticationManager;
    @Mock private JwtService jwt;

    @Test
    void registerNormalizesIdentityAndReturnsToken() {
        when(encoder.encode("strong-password")).thenReturn("password-hash");
        when(jwt.generate("user@example.com")).thenReturn("signed-token");
        when(jwt.expiresInSeconds()).thenReturn(3600L);
        AuthService service = new AuthService(users, encoder, authenticationManager, jwt);

        AuthResponse response = service.register(
                new RegisterRequest("  USER@Example.COM ", "strong-password", "  Example User  "));

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(users).saveAndFlush(captor.capture());
        assertThat(captor.getValue().getEmail()).isEqualTo("user@example.com");
        assertThat(captor.getValue().getName()).isEqualTo("Example User");
        assertThat(captor.getValue().getPassword()).isEqualTo("password-hash");
        assertThat(response.accessToken()).isEqualTo("signed-token");
        assertThat(response.tokenType()).isEqualTo("Bearer");
        assertThat(response.expiresIn()).isEqualTo(3600L);
    }

    @Test
    void registerRejectsAnExistingEmailWithoutHashingPassword() {
        when(users.existsByEmailIgnoreCase("user@example.com")).thenReturn(true);
        AuthService service = new AuthService(users, encoder, authenticationManager, jwt);

        assertThatThrownBy(() -> service.register(
                new RegisterRequest("USER@example.com", "strong-password", "User")))
                .isInstanceOf(ConflictException.class);
        verify(encoder, never()).encode(org.mockito.ArgumentMatchers.anyString());
        verify(users, never()).saveAndFlush(org.mockito.ArgumentMatchers.any());
    }
}
