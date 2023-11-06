package com.usermanagment.user.domain;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<User> findUserById(Integer id);

    Optional<User> findUserByUsername(String username);

    Optional<User> findUserByEmail(String email);
}
