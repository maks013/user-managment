package com.usermanagment.confirmationtoken.domain;

import com.usermanagment.confirmationtoken.dto.TokenDto;
import com.usermanagment.confirmationtoken.exception.TokenConfirmedException;
import com.usermanagment.confirmationtoken.exception.TokenNotFoundException;
import com.usermanagment.confirmationtoken.exception.TokenExpiredException;
import com.usermanagment.user.domain.UserFacade;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
@AllArgsConstructor
public class TokenFacade {

    private final TokenService tokenService;
    private final UserFacade userFacade;

    private static final String CONFIRMATION_LINK = "http://localhost:8080/confirmation-token/confirm?token=";

    public List<TokenDto> readAllTokens() {
        return tokenService.findAll();
    }

    @Transactional
    public String confirmToken(String token) {
        Token confirmationToken = tokenService
                .getToken(token)
                .orElseThrow(TokenNotFoundException::new);

        if (confirmationToken.getConfirmedAt() != null) {
            throw new TokenConfirmedException();
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new TokenExpiredException();
        }

        tokenService.setConfirmedAt(token);
        userFacade.enableAppUser(
                confirmationToken.getUser().getEmail());
        return "confirmed";
    }

    public String createTokenLink(String email) {
        final String confirmationToken = UUID.randomUUID().toString();
        Token token = Token.builder()
                .token(confirmationToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(userFacade.getByEmail(email))
                .build();

        tokenService.saveConfirmationToken(token);
        return CONFIRMATION_LINK + confirmationToken;
    }

    public void deleteToken(Integer id) {
        tokenService.deleteToken(id);
    }
}
