package it.financemanager.infrastructure.security;

import it.financemanager.auth.AccessTokenIssuer;
import org.springframework.stereotype.Component;

@Component
class JwtAccessTokenAdapter implements AccessTokenIssuer {
    private final JwtService jwtService;

    JwtAccessTokenAdapter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public String issue(String subject) {
        return jwtService.generate(subject);
    }

    @Override
    public long expiresInSeconds() {
        return jwtService.expiresInSeconds();
    }
}
