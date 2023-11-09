package com.usermanagment.user.domain;

interface UserValidationService {
    void isUsernameAvailable(String username);

    void isEmailAvailable(String email);

    void validateEmailFormat(String email);
}
