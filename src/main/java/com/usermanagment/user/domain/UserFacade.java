package com.usermanagment.user.domain;

import com.usermanagment.user.dto.*;
import com.usermanagment.user.exception.IncorrectPasswordException;
import com.usermanagment.user.exception.InvalidUpdate;
import com.usermanagment.user.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class UserFacade {

    private final UserRepository userRepository;
    private final UserValidationService validationService;
    private final BCryptPasswordEncoder bCryptEncoder;

    public UserDto registerUser(RegistrationRequest registrationRequest) {
        verifyAvailability(registrationRequest.getEmail(), registrationRequest.getUsername());
        verifyEmailFormat(registrationRequest.getEmail());

        String encodedPassword = bCryptEncoder.encode(registrationRequest.getPassword());

        User user = User.builder()
                .username(registrationRequest.getUsername())
                .password(encodedPassword)
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

    public UserDtoWithPassword getUserWithPasswordById(Integer id) {
        return userRepository.findUserById(id)
                .map(User::toDtoWithPassword)
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

    public void deleteUserById(Integer id) {
        if (id == null || !userRepository.existsById(id)) {
            throw new UserNotFoundException();
        }
        userRepository.deleteById(id);
    }

    public void updateUser(Integer id, UpdateUserDto updateUserDto) {
        if (updateUserDto.getUsername().isBlank() && updateUserDto.getEmail().isBlank()) {
            throw new InvalidUpdate();
        }

        if (!updateUserDto.getEmail().isEmpty()) {
            verifyEmailFormat(updateUserDto.getEmail());
        }

        verifyAvailability(updateUserDto.getEmail(), updateUserDto.getUsername());

        User user = userRepository.findUserById(id)
                .orElseThrow(UserNotFoundException::new);
        UserMapper.mapToUpdate(user, updateUserDto);

        userRepository.save(user);
    }

    public void updatePassword(Integer id, UpdatePasswordDto updatePasswordDto) {
        if (updatePasswordDto.getNewPassword().isBlank() || updatePasswordDto.getOldPassword().isBlank()) {
            throw new IncorrectPasswordException();
        }

        User user = userRepository.findUserById(id).orElseThrow(UserNotFoundException::new);

        if (!bCryptEncoder.matches(updatePasswordDto.getOldPassword(), user.getPassword())
                || bCryptEncoder.matches(updatePasswordDto.getNewPassword(), user.getPassword())) {
             throw new IncorrectPasswordException();
        }

        user.setPassword(bCryptEncoder.encode(updatePasswordDto.getNewPassword()));
        userRepository.save(user);
    }

    private void verifyAvailability(String email, String username) {
        validationService.isUsernameAvailable(username);
        validationService.isEmailAvailable(email);
    }

    private void verifyEmailFormat(String email) {
        validationService.validateEmailFormat(email);
    }
}
