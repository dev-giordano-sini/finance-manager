package it.financemanager.auth;

/** Output port for issuing access tokens. */
public interface AccessTokenIssuer {
  String issue(String subject);
  long expiresInSeconds();
}
