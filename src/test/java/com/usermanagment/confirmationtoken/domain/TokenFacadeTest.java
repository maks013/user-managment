package com.usermanagment.confirmationtoken.domain;

import com.usermanagment.confirmationtoken.exception.TokenConfirmedException;
import com.usermanagment.confirmationtoken.exception.TokenExpiredException;
import com.usermanagment.confirmationtoken.exception.TokenNotFoundException;
import com.usermanagment.user.domain.InMemoryUserRepository;
import com.usermanagment.user.domain.UserFacade;
import com.usermanagment.user.domain.UserFacadeTestConfig;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class TokenFacadeTest {

    private final UserFacade userFacade = new UserFacadeTestConfig(new InMemoryUserRepository())
            .userFacadeConfigForTests();
    private final InMemoryTokenRepository repository = new InMemoryTokenRepository();
    private static DateTimeProvider dateTimeProvider = new DateTimeProvider();

    private final TokenFacade tokenFacade = new TokenFacade(userFacade, repository, dateTimeProvider);

    private void setDateTimeProvider(DateTimeProvider dateTimeProvider) {
        TokenFacadeTest.dateTimeProvider = dateTimeProvider;
    }

    @Test
    void should_find_all_tokens() {
        //given
        final int size = tokenFacade.readAllTokens().size();
        //when
        //then
        assertEquals(3, size);
    }

    @Test
    void should_throw_exception_while_confirming_token_when_it_is_confirmed() {
        //given
        final String token = "111";
        //when
        tokenFacade.confirmToken(token);
        //then
        assertThrows(TokenConfirmedException.class, () -> tokenFacade.confirmToken(token));
    }

    @Test
    void should_delete_token_successfully() {
        //given
        final String token = tokenFacade.createTokenLink("email1@example.com");
        //when
        tokenFacade.deleteToken(1);
        //then
        assertThrows(TokenNotFoundException.class, () -> tokenFacade.confirmToken(token));
    }

    @Test
    void should_create_token_link_successfully() {
        //given
        final String email = "email1@example.com";
        final String expectedLink = "http://localhost:8080/confirmation-token/confirm?token=";
        //when
        String tokenLink = tokenFacade.createTokenLink(email);
        //then
        assertTrue(tokenLink.startsWith("http://localhost:8080/confirmation-token/confirm?token="));
    }

    @Test
    void should_confirm_token_successfully() {
        //given
        //when
        String result = tokenFacade.confirmToken("111");
        //then
        assertEquals("confirmed", result);
    }

    @Test
    void should_throw_token_expired_exception_when_confirming_token_when_is_expired() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime expiredDateTime = currentDateTime.minusMinutes(30);
        //and
        DateTimeProvider mockedDateTimeProvider = Mockito.mock(DateTimeProvider.class);
        when(mockedDateTimeProvider.getCurrentDateTime()).thenReturn(currentDateTime);

        setDateTimeProvider(mockedDateTimeProvider);

        //when
        Token expiredToken = Token.builder()
                .token("expiredToken")
                .createdAt(currentDateTime.minusHours(1))
                .expiresAt(expiredDateTime)
                .userId(3)
                .build();

        repository.save(expiredToken);

        //then
        assertThrows(TokenExpiredException.class, () -> tokenFacade.confirmToken("expiredToken"));
    }
}
