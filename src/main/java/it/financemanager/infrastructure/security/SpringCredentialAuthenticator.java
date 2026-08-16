package it.financemanager.infrastructure.security;

import it.financemanager.auth.CredentialAuthenticator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
class SpringCredentialAuthenticator implements CredentialAuthenticator {
    private final AuthenticationManager authenticationManager;

    SpringCredentialAuthenticator(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void authenticate(String email, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));
    }
}
