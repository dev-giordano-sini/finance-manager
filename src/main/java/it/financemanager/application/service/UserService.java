package it.financemanager.application.service;
import it.financemanager.application.port.in.UserUseCase; import it.financemanager.application.port.out.*; import it.financemanager.domain.model.User;
public final class UserService implements UserUseCase { private final UserResolver resolver; public UserService(CurrentActorPort actor,UserPort users){resolver=new UserResolver(actor,users);} public CurrentUser current(){User u=resolver.current();return new CurrentUser(u.name(),u.surname(),u.email());} }
