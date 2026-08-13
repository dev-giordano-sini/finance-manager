package it.financemanager.application.port.out;
public interface PasswordPort { String encode(String raw); boolean matches(String raw, String encoded); }
