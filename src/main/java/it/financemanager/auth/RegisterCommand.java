package it.financemanager.auth;

/**
 * Framework-neutral registration command accepted by the application core.
 */
public record RegisterCommand(String name, String surname, String email,
                              String password) {
}
