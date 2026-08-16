package it.financemanager.auth;

/**
 * Output port for password hashing.
 */
public interface PasswordHasher {
    String hash(String rawPassword);
}
