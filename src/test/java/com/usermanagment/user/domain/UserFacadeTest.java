package com.usermanagment.user.domain;

import com.usermanagment.user.dto.*;
import com.usermanagment.user.exception.*;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

class UserFacadeTest {

    private final UserFacadeTestConfig userFacadeTestConfig = new UserFacadeTestConfig(new InMemoryUserRepository());

    UserFacade userFacade = userFacadeTestConfig.userFacadeConfigForTests();
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Test
    void should_throw_exception_while_registering_when_email_address_format_is_invalid() {
        //given
        final String invalidEmailExample = "invalidEmailExample";
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
        final String username = "exampleUsername";
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
    void should_register_user_successfully() {
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
    void should_find_user_by_username() {
        //given
        //when
        UserDto userDto = userFacade.getUserByUsername("user1");
        //then
        assertAll(
                () -> assertEquals(1, userDto.getId()),
                () -> assertEquals("email1@example.com", userDto.getEmail())
        );
    }

    @Test
    void should_throw_exception_when_user_not_found_by_username() {
        //given
        final String usernameOfNotExistingUser = "example";
        //when
        //then
        assertThrows(UserNotFoundException.class, () -> userFacade.getUserByUsername(usernameOfNotExistingUser),
                "User not found");
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
        assertThrows(UserNotFoundException.class, () -> userFacade.getUserByEmail(emailOfNotExistingUser),
                "User not found");
    }

    @Test
    void should_delete_user_by_username() {
        //given
        final int sizeBeforeDeleteUser = userFacade.readAllUsers().size();
        //when
        userFacade.deleteUser(1, "user1");
        final int sizeAfterDeleteUser = userFacade.readAllUsers().size();
        //then
        assertAll(
                () -> assertEquals(3, sizeBeforeDeleteUser),
                () -> assertEquals(2, sizeAfterDeleteUser)
        );
    }

    @Test
    void should_throw_userNotFoundException_while_deleting_user_when_user_with_that_username_notExists() {
        //given
        final String usernameOfNotExistingUser = "example";
        //when
        //then
        assertThrows(UserNotFoundException.class, () -> userFacade.deleteUser(1, "example"));
    }

    @Test
    void should_throw_userNotFoundException_while_deleting_user_when_id_is_null() {
        //given
        //when
        //then
        assertThrows(InvalidUserIdException.class, () -> userFacade.deleteUser(null, "user1"));
    }

    @Test
    void should_updateUser_data() {
        //given
        final String usernameBefore = "user1", emailBefore = "email1@example.com";
        final String usernameAfter = "updatedUsername", emailAfter = "updated@updated.com";

        //and
        UpdateUserDto updateUserDto = UpdateUserDto.builder()
                .username(usernameAfter)
                .email(emailAfter)
                .build();
        //when
        userFacade.updateUser(1, updateUserDto, usernameBefore);
        UserDto userAfterUpdate = userFacade.getUserByUsername("updatedUsername");
        //then
        assertAll(
                () -> assertNotEquals(usernameBefore, userAfterUpdate.getUsername()),
                () -> assertNotEquals(emailBefore, userAfterUpdate.getEmail())
        );
    }

    @Test
    void should_throw_exception_while_updating_when_email_address_format_is_invalid() {
        //given
        final String invalidEmailExample = "invalidEmailExample";
        //when
        UpdateUserDto updateUserDto = UpdateUserDto.builder()
                .username("")
                .email(invalidEmailExample)
                .build();
        //then
        assertThrows(InvalidEmailFormatException.class, () -> userFacade.updateUser(1, updateUserDto, "user1"));
    }

    @Test
    void should_throw_exception_while_updating_user_data_when_email_is_already_taken() {
        //given
        final String username = "user1";
        final int userId = 1;
        final String takenEmail = userFacade.getUserByUsername("user2").getEmail();
        //when
        UpdateUserDto updateUserDto = UpdateUserDto.builder()
                .username("")
                .email(takenEmail)
                .build();
        //then
        assertThrows(TakenEmailException.class, () -> userFacade.updateUser(userId, updateUserDto, username));
    }

    @Test
    void should_throw_exception_while_updating_user_data_when_username_is_already_taken() {
        //given
        final String user = "user1", takenUsername = "user2";
        final int userId = userFacade.getUserByUsername(user).getId();
        //when
        UpdateUserDto updateUserDto = UpdateUserDto.builder()
                .username(takenUsername)
                .email("example@updated.com")
                .build();
        //then
        assertThrows(TakenUsernameException.class, () -> userFacade.updateUser(userId, updateUserDto, user));
    }

    @Test
    void should_throw_exception_when_old_password_is_blank() {
        //given
        UpdatePasswordDto updatePasswordDto = UpdatePasswordDto.builder()
                .oldPassword("")
                .newPassword("newPassword")
                .build();
        //when
        //then
        assertThrows(IncorrectPasswordException.class,
                () -> userFacade.updatePassword(1, updatePasswordDto, "user1"),
                "Old password is blank");
    }

    @Test
    void should_throw_exception_when_new_password_is_blank() {
        //given
        UpdatePasswordDto updatePasswordDto = UpdatePasswordDto.builder()
                .oldPassword("oldPassword")
                .newPassword("")
                .build();
        //when
        //then
        assertThrows(IncorrectPasswordException.class,
                () -> userFacade.updatePassword(1, updatePasswordDto, "user1"),
                "New password is blank");
    }

    @Test
    void should_throw_exception_when_old_password_is_incorrect() {
        //given
        UpdatePasswordDto updatePasswordDto = UpdatePasswordDto.builder()
                .oldPassword("incorrectOldPassword")
                .newPassword("newPassword")
                .build();
        //when
        //then
        assertThrows(IncorrectPasswordException.class,
                () -> userFacade.updatePassword(1, updatePasswordDto,"user1"),
                "Password is incorrect");
    }

    @Test
    void should_throw_exception_when_new_password_matches_old_password() {
        //given
        UpdatePasswordDto updatePasswordDto = UpdatePasswordDto.builder()
                .oldPassword("oldPassword")
                .newPassword("oldPassword")
                .build();
        //when
        //then
        assertThrows(IncorrectPasswordException.class,
                () -> userFacade.updatePassword(1, updatePasswordDto, "user1"),
                "Password is incorrect");
    }

    @Test
    void should_update_password_successfully() {
        //given
        final int userId = 1;
        final String username = "user1";
        final String oldPassword = "password1", newPassword = "newPassword";
        //and
        UpdatePasswordDto updatePasswordDto = UpdatePasswordDto.builder()
                .oldPassword(oldPassword)
                .newPassword(newPassword)
                .build();
        //when
        userFacade.updatePassword(userId, updatePasswordDto, username);
        UserDtoWithPassword userAfterUpdate = userFacade.getUserWithPasswordByUsername(username);
        //then
        assertTrue(bCryptPasswordEncoder.matches(newPassword, userAfterUpdate.getPassword()));
    }

}
