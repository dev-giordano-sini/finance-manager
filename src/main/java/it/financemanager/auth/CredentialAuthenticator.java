package it.financemanager.auth;

/** Output port used to verify a user's credentials. */
public interface CredentialAuthenticator {
  void authenticate(String email, String password);
}
