package com.usermanagment.user.domain;

import com.usermanagment.user.exception.InvalidEmailFormatException;
import com.usermanagment.user.exception.TakenEmailException;
import com.usermanagment.user.exception.TakenUsernameException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class UserDataValidator implements UserValidationService {

    private final UserRepository userRepository;

    UserDataValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private static final String EMAIL_PATTERN =
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$";

    @Override
    public void isUsernameAvailable(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new TakenUsernameException();
        }
    }

    @Override
    public void isEmailAvailable(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new TakenEmailException();
        }
    }

    @Override
    public void validateEmailFormat(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new InvalidEmailFormatException();
        }
    }
}
