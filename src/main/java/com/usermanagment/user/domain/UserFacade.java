package com.usermanagment.user.domain;

import com.usermanagment.user.dto.RegistrationRequest;
import com.usermanagment.user.dto.UserDto;
import com.usermanagment.user.exception.UserNotFoundException;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class UserFacade {

    private final UserRepository userRepository;
    private final UserValidationService validationService;

    public UserDto registerUser(RegistrationRequest registrationRequest) {
        validationService.validateEmailFormat(registrationRequest.getEmail());
        validationService.isUsernameAvailable(registrationRequest.getUsername());
        validationService.isEmailAvailable(registrationRequest.getEmail());

        User user = User.builder()
                .username(registrationRequest.getUsername())
                .password(registrationRequest.getPassword())
                .email(registrationRequest.getEmail())
                .build();

        return userRepository.save(user).toDto();
    }

    public List<UserDto> readAllUsers() {
        return userRepository.findAll().stream()
                .map(User::toDto)
                .collect(Collectors.toList());
    }

    public UserDto getUserById(Integer id) {
        return userRepository.findUserById(id)
                .map(User::toDto)
                .orElseThrow(UserNotFoundException::new);
    }

    public UserDto getUserByEmail(String email) {
        return userRepository.findUserByEmail(email)
                .map(User::toDto)
                .orElseThrow(UserNotFoundException::new);
    }

    public UserDto getUserByUsername(String username) {
        return userRepository.findUserByUsername(username)
                .map(User::toDto)
                .orElseThrow(UserNotFoundException::new);
    }

}
