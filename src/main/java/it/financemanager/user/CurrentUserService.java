package it.financemanager.user;

import it.financemanager.common.exception.ResourceNotFoundException;

public class CurrentUserService
    implements CurrentUserUseCase, CurrentUserProvider {
  private final UserStore repository;
  private final AuthenticatedIdentity identity;
  public CurrentUserService(UserStore repository,
                            AuthenticatedIdentity identity) {
    this.repository = repository;
    this.identity = identity;
  }
  public User get() {
    String email = identity.username();
    return repository.findByEmailIgnoreCase(email).orElseThrow(
        () -> new ResourceNotFoundException("User", 0L));
  }

  public CurrentUserResponse getCurrentUser() {
    User currentUser = get();
    return CurrentUserResponse.from(currentUser);
  }
}
