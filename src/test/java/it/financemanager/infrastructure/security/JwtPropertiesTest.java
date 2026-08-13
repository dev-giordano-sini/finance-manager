package it.financemanager.infrastructure.security;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Duration;
import org.junit.jupiter.api.Test;

class JwtPropertiesTest {
    @Test
    void rejectsSigningSecretsShorterThan256Bits() {
        assertThatThrownBy(() -> new JwtProperties("too-short", Duration.ofHours(1)))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("at least 32 bytes");
    }

    @Test
    void rejectsNonPositiveExpiration() {
        assertThatThrownBy(() -> new JwtProperties("12345678901234567890123456789012", Duration.ZERO))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("must be positive");
    }
}
