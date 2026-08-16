package it.financemanager.infrastructure.application;

import it.financemanager.auth.*;
import it.financemanager.role.RoleStore;
import it.financemanager.user.UserStore;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
class TransactionalAuthUseCase implements AuthUseCase {
  private final AuthService delegate;
  TransactionalAuthUseCase(UserStore users, PasswordHasher hasher,
                           CredentialAuthenticator authenticator,
                           AccessTokenIssuer tokens, RoleStore roles) {
    delegate = new AuthService(users, hasher, authenticator, tokens, roles);
  }
  @Override
  @Transactional
  public AuthResponse register(RegisterCommand command) {
    return delegate.register(command);
  }
  @Override
  public AuthResponse login(LoginCommand command) {
    return delegate.login(command);
  }
}
