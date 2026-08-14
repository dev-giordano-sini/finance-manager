package it.financemanager.infrastructure.security;

import it.financemanager.user.AuthenticatedIdentity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
class SpringSecurityIdentityAdapter implements AuthenticatedIdentity {
    @Override
    public String username() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
