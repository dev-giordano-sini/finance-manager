package it.financemanager.infrastructure.security;

import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(String secret, Duration expiration) {
  public JwtProperties {
    if (secret == null ||
        secret.getBytes(java.nio.charset.StandardCharsets.UTF_8).length < 32) {
      throw new IllegalArgumentException(
          "jwt.secret must be at least 32 bytes");
    }
    if (expiration == null || expiration.isNegative() || expiration.isZero()) {
      throw new IllegalArgumentException("jwt.expiration must be positive");
    }
  }
}
