package it.financemanager.infrastructure.security;

import it.financemanager.auth.PasswordHasher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
class SpringPasswordHasher implements PasswordHasher {
  private final PasswordEncoder encoder;

  SpringPasswordHasher(PasswordEncoder encoder) { this.encoder = encoder; }

  @Override
  public String hash(String rawPassword) {
    return encoder.encode(rawPassword);
  }
}
