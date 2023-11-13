package com.usermanagment.user.domain;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@AllArgsConstructor
public class UserFacadeTestConfig {

    private final InMemoryUserRepository inMemoryUserRepository;

    public UserFacade userFacadeConfigForTests() {
        return new UserFacade(
                inMemoryUserRepository,
                new UserDataValidator(inMemoryUserRepository),
                new BCryptPasswordEncoder());
    }

}
