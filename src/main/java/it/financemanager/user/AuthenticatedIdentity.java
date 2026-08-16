package it.financemanager.user;

/**
 * Output port exposing the authenticated principal without leaking a security
 * framework.
 */
public interface AuthenticatedIdentity {
  String username();
}
