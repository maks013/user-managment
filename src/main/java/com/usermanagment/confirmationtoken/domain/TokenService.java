package com.usermanagment.confirmationtoken.domain;

import com.usermanagment.confirmationtoken.dto.TokenDto;

import java.util.List;
import java.util.Optional;


interface TokenService {
    void saveConfirmationToken(Token token);

    Optional<Token> getToken(String token);

    int setConfirmedAt(String token);

    List<TokenDto> findAll();

    void deleteToken(Integer id);
}
