package com.usermanagment.user.domain;


import com.usermanagment.user.dto.UserDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

class InMemoryUserRepository implements UserRepository {

    HashMap<Integer, User> inMemoryUserDatabase = new HashMap<>();
    private static final Integer ACCOUNT_ID = 1;

    private static final User USER1 = new User(1, "user1", "password1", "email1@example.com");
    private static final User USER2 = new User(2, "user2", "password2", "email2@example.com");
    private static final User USER3 = new User(3, "user3", "password2", "email3@example.com");

    InMemoryUserRepository() {
        inMemoryUserDatabase.put(1, USER1);
        inMemoryUserDatabase.put(2, USER2);
        inMemoryUserDatabase.put(3, USER3);
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(inMemoryUserDatabase.values());
    }

    @Override
    public User save(User user) {
        inMemoryUserDatabase.put(ACCOUNT_ID, user);
        return user;
    }

    @Override
    public boolean existsByUsername(String username) {
        return inMemoryUserDatabase.values()
                .stream()
                .anyMatch(user -> user.getUsername().equals(username));
    }

    @Override
    public boolean existsByEmail(String email) {
        return inMemoryUserDatabase.values()
                .stream()
                .anyMatch(user -> user.getEmail().equals(email));
    }

    @Override
    public Optional<User> findUserById(Integer id) {
        return Optional.ofNullable(inMemoryUserDatabase.get(id));
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return inMemoryUserDatabase.values()
                .stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return inMemoryUserDatabase.values()
                .stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }
}
