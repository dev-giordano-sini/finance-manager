package it.financemanager.infrastructure.application;

import it.financemanager.user.AuthenticatedIdentity;
import it.financemanager.user.CurrentUserService;
import it.financemanager.user.UserStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ApplicationCoreConfig {
  @Bean
  CurrentUserService currentUserService(UserStore users,
                                        AuthenticatedIdentity identity) {
    return new CurrentUserService(users, identity);
  }
}
