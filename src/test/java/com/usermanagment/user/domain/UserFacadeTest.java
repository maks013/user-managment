package com.usermanagment.user.domain;

import com.usermanagment.user.dto.RegistrationRequest;
import com.usermanagment.user.dto.UserDto;
import com.usermanagment.user.exception.InvalidEmailFormatException;
import com.usermanagment.user.exception.TakenEmailException;
import com.usermanagment.user.exception.TakenUsernameException;
import com.usermanagment.user.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserFacadeTest {

    private final UserFacadeTestConfig userFacadeTestConfig = new UserFacadeTestConfig(new InMemoryUserRepository());

    UserFacade userFacade = userFacadeTestConfig.userFacadeConfigForTests();

    @Test
    void should_throw_exception_when_email_address_format_is_invalid() {
        //given
        String invalidEmailExample = "invalidEmailExample";
        //when
        RegistrationRequest registrationRequest = RegistrationRequest.builder()
                .username("example")
                .password("somePassword")
                .email(invalidEmailExample)
                .build();
        //then
        assertThrows(InvalidEmailFormatException.class,
                () -> userFacade.registerUser(registrationRequest), "Format of email address is invalid");
    }

    @Test
    void should_throw_exception_when_username_is_already_taken() {
        //given
        String username = "exampleUsername";
        //and
        RegistrationRequest registrationRequest = RegistrationRequest.builder()
                .username(username)
                .password("somePassword")
                .email("someEmail@example.com")
                .build();
        RegistrationRequest registrationRequestWithSameUsername = RegistrationRequest.builder()
                .username(username)
                .password("somePassword")
                .email("someEmail2@example.com")
                .build();
        //when
        userFacade.registerUser(registrationRequest);
        //then
        assertThrows(TakenUsernameException.class,
                () -> userFacade.registerUser(registrationRequestWithSameUsername),
                "This username is already taken");
    }

    @Test
    void should_throw_exception_when_email_is_already_taken() {
        //given
        String email = "someEmail@example.com";
        //and
        RegistrationRequest registrationRequest = RegistrationRequest.builder()
                .username("usernameExample1")
                .password("somePassword")
                .email(email)
                .build();
        RegistrationRequest registrationRequestWithSameEmail = RegistrationRequest.builder()
                .username("usernameExample2")
                .password("somePassword")
                .email(email)
                .build();
        //when
        userFacade.registerUser(registrationRequest);
        //then
        assertThrows(TakenEmailException.class,
                () -> userFacade.registerUser(registrationRequestWithSameEmail),
                "This username is already taken");
    }

    @Test
    void should_register_user() {
        //given
        RegistrationRequest registrationRequest = RegistrationRequest.builder()
                .username("usernameExample1")
                .password("somePassword")
                .email("email@someEmail.com")
                .build();
        //when
        UserDto userDto = userFacade.registerUser(registrationRequest);
        //then
        assertAll(
                () -> assertEquals(registrationRequest.getUsername(), userDto.getUsername()),
                () -> assertEquals(registrationRequest.getEmail(), userDto.getEmail())
        );
    }


    @Test
    void should_findAll_users() {
        //given
        //when
        final int users = userFacade.readAllUsers().size();
        //then
        assertEquals(3, users);
    }

    @Test
    void should_find_user_by_id() {
        //given
        //when
        UserDto userDto = userFacade.getUserById(1);
        //then
        assertAll(
                () -> assertEquals("user1", userDto.getUsername()),
                () -> assertEquals("email1@example.com", userDto.getEmail())
        );
    }

    @Test
    void should_throw_exception_when_user_not_found_by_id() {
        //given
        final int idOfNotExistingUser = 4;
        //when
        //then
        assertThrows(UserNotFoundException.class, () -> userFacade.getUserById(idOfNotExistingUser), "User not found");
    }

    @Test
    void should_find_user_by_username() {
        //given
        //when
        UserDto userDto = userFacade.getUserByUsername("user2");
        //then
        assertAll(
                () -> assertEquals("email2@example.com", userDto.getEmail()),
                () -> assertEquals(2, userDto.getId())
        );
    }

    @Test
    void should_throw_exception_when_user_not_found_by_username() {
        //given
        final String usernameOfNotExistingUser = "example";
        //when
        //then
        assertThrows(UserNotFoundException.class, () -> userFacade.getUserByUsername(usernameOfNotExistingUser), "User not found");
    }

    @Test
    void should_find_user_by_email() {
        //given
        //when
        UserDto userDto = userFacade.getUserByEmail("email3@example.com");
        //then
        assertAll(
                () -> assertEquals("user3", userDto.getUsername()),
                () -> assertEquals(3, userDto.getId())
        );
    }

    @Test
    void should_throw_exception_when_user_not_found_by_email() {
        //given
        final String emailOfNotExistingUser = "example@example.com";
        //when
        //then
        assertThrows(UserNotFoundException.class, () -> userFacade.getUserByEmail(emailOfNotExistingUser), "User not found");
    }

    @Test
    void should_delete_user_by_id() {
        //given
        int sizeBeforeDeleteUser = userFacade.readAllUsers().size();
        //when
        userFacade.deleteUserById(1);
        int sizeAfterDeleteUser = userFacade.readAllUsers().size();
        //then
        assertAll(
                () -> assertEquals(3, sizeBeforeDeleteUser),
                () -> assertEquals(2, sizeAfterDeleteUser)
        );
    }

    @Test
    void should_throw_userNotFoundException_while_deleting_user_when_user_with_that_id_notExists() {
        //given
        int notExistingId = 125;
        //when
        //then
        assertThrows(UserNotFoundException.class, () -> userFacade.deleteUserById(notExistingId));
    }

    @Test
    void should_throw_userNotFoundException_while_deleting_user_when_id_is_null() {
        //given
        //when
        //then
        assertThrows(UserNotFoundException.class, () -> userFacade.deleteUserById(null));
    }
}
