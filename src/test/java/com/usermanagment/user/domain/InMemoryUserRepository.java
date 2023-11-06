package com.usermanagment.user.domain;


import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

class InMemoryUserRepository implements UserRepository {

    HashMap<Integer, User> inMemoryUserDatabase = new HashMap<>();
    private static final BCryptPasswordEncoder bcryptPassword = new BCryptPasswordEncoder();
    private static final Integer ACCOUNT_ID = 1;

    private static final User USER1 = new User(1, "user1",
            bcryptPassword.encode("password1"), "email1@example.com");
    private static final User USER2 = new User(2, "user2",
            bcryptPassword.encode("password2"), "email2@example.com");
    private static final User USER3 = new User(3, "user3",
            bcryptPassword.encode("password2"), "email3@example.com");

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
    public boolean existsById(Integer id) {
        return inMemoryUserDatabase.containsKey(id);
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

    @Override
    public void deleteById(Integer id) {
        inMemoryUserDatabase.remove(id);
    }

    @Override
    public <S extends User> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<User> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public List<User> findAllById(Iterable<Integer> integers) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void delete(User entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends User> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends User> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends User> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<User> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public User getOne(Integer integer) {
        return null;
    }

    @Override
    public User getById(Integer integer) {
        return null;
    }

    @Override
    public User getReferenceById(Integer integer) {
        return null;
    }

    @Override
    public <S extends User> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends User> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends User> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends User> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends User> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends User> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends User, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public List<User> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return null;
    }
}
