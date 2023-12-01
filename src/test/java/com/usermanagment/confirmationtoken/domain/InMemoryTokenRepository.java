package com.usermanagment.confirmationtoken.domain;

import com.usermanagment.confirmationtoken.exception.TokenNotFoundException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

class InMemoryTokenRepository implements TokenRepository {

    static HashMap<Integer, Token> inMemoryTokenDatabase = new HashMap<>();

    private Token token1 = new Token(1, "111",
            LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), null, 1);
    private Token token2 = new Token(1, "222",
            LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), LocalDateTime.now().plusMinutes(10), 2);

    InMemoryTokenRepository() {
        inMemoryTokenDatabase.put(1, token1);
        inMemoryTokenDatabase.put(2, token2);
    }

    @Override
    public Optional<Token> findByToken(String token) {
        for (Token t : inMemoryTokenDatabase.values()) {
            if (t.getToken().equals(token)) {
                return Optional.of(t);
            }
        }
        return Optional.empty();
    }

    @Override
    public void updateConfirmedAt(String token, LocalDateTime confirmedAt) {
        Token token1 = findByToken(token).orElseThrow(TokenNotFoundException::new);
        token1.setConfirmedAt(confirmedAt);
    }

    @Override
    public List<Token> findAll() {
        return new ArrayList<>(inMemoryTokenDatabase.values());
    }

    @Override
    public List<Token> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Token> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Token> findAllById(Iterable<Integer> integers) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public void delete(Token entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends Token> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public Token save(Token entity) {
        inMemoryTokenDatabase.put(entity.getId(), entity);
        return null;
    }

    @Override
    public <S extends Token> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Token> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Token> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Token> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Token> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Token getOne(Integer integer) {
        return null;
    }

    @Override
    public Token getById(Integer integer) {
        return null;
    }

    @Override
    public Token getReferenceById(Integer integer) {
        return null;
    }

    @Override
    public <S extends Token> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Token> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Token> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Token> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Token> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Token> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Token, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
