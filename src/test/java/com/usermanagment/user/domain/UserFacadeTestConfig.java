package com.usermanagment.user.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
class UserFacadeTestConfig {

    private final InMemoryUserRepository inMemoryUserRepository;

    UserFacade userFacadeConfigForTests() {
        return new UserFacade(inMemoryUserRepository, new UserDataValidator(inMemoryUserRepository));
    }

}
