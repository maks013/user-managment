package com.usermanagment.user.domain;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@AllArgsConstructor
class UserFacadeTestConfig {

    private final InMemoryUserRepository inMemoryUserRepository;

    UserFacade userFacadeConfigForTests() {
        return new UserFacade(
                inMemoryUserRepository,
                new UserDataValidator(inMemoryUserRepository),
                new BCryptPasswordEncoder());
    }

}
