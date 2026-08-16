package it.financemanager.user;

/** Output port that resolves the user associated with the current request. */
public interface CurrentUserProvider {
  User get();
}
