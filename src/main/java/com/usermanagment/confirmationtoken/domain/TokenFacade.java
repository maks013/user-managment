package com.usermanagment.confirmationtoken.domain;

import com.usermanagment.confirmationtoken.dto.TokenDto;
import com.usermanagment.confirmationtoken.exception.TokenConfirmedException;
import com.usermanagment.confirmationtoken.exception.TokenExpiredException;
import com.usermanagment.confirmationtoken.exception.TokenNotFoundException;
import com.usermanagment.user.domain.UserFacade;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class TokenFacade {

    private final UserFacade userFacade;
    private final TokenRepository repository;
    private final DateTimeProvider dateTimeProvider;

    private static final String CONFIRMATION_LINK = "http://localhost:8080/confirmation-token/confirm?token=";

    public List<TokenDto> readAllTokens() {
        return repository.findAll().stream()
                .map(Token::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public String confirmToken(String token) {
        Token confirmationToken = repository.findByToken(token)
                .orElseThrow(TokenNotFoundException::new);

        if (confirmationToken.getConfirmedAt() != null) {
            throw new TokenConfirmedException();
        }

        if (confirmationToken.getExpiresAt().isBefore(dateTimeProvider.getCurrentDateTime())) {
            throw new TokenExpiredException();
        }

        repository.updateConfirmedAt(token, dateTimeProvider.getCurrentDateTime());

        userFacade.enableAppUser(confirmationToken.getUser().getEmail());
        return "confirmed";
    }

    public String createTokenLink(String email) {
        final String confirmationToken = UUID.randomUUID().toString();
        Token token = Token.builder()
                .token(confirmationToken)
                .createdAt(dateTimeProvider.getCurrentDateTime())
                .expiresAt(dateTimeProvider.getCurrentDateTime().plusMinutes(15))
                .user(userFacade.getByEmail(email))
                .build();

        repository.save(token);
        return CONFIRMATION_LINK + confirmationToken;
    }

    public void deleteToken(Integer id) {
        repository.deleteById(id);
    }
}
