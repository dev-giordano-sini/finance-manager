package it.financemanager.application.port.out;
public interface TokenPort { String generate(String subject); long expiresInSeconds(); }
