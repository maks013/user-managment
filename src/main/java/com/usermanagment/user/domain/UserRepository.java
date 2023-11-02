package com.usermanagment.user.domain;

import java.util.List;
import java.util.Optional;

interface UserRepository {

    List<User> findAll();

    User save(User user);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<User> findUserById(Integer id);

    Optional<User> findUserByUsername(String username);

    Optional<User> findUserByEmail(String email);
}
