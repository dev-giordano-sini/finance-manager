package it.financemanager.auth;

/** Framework-neutral login command accepted by the application core. */
public record LoginCommand(String email, String password) {}
