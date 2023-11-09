package com.usermanagment.user.domain;

import com.usermanagment.user.dto.*;
import com.usermanagment.user.exception.*;
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

        User user = User.builder()
                .username(registrationRequest.getUsername())
                .password(bCryptEncoder.encode(registrationRequest.getPassword()))
                .email(registrationRequest.getEmail())
                .role(User.Role.USER)
                .enabled(false)
                .build();

        return userRepository.save(user).toDto();
    }

    public List<UserDto> readAllUsers() {
        return userRepository.findAll().stream()
                .map(User::toDto)
                .collect(Collectors.toList());
    }

    public UserDto getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(User::toDto)
                .orElseThrow(UserNotFoundException::new);
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    public UserDto getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(User::toDto)
                .orElseThrow(UserNotFoundException::new);
    }

    public UserDtoWithPassword getUserWithPasswordByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(User::toDtoWithPassword)
                .orElseThrow(UserNotFoundException::new);
    }

    public void deleteUser(Integer id, String username) {
        verifyUserOwnership(id, username);
        verifyUserEnabled(username);

        userRepository.deleteById(id);
    }

    public void updateUser(Integer id, UpdateUserDto updateUserDto, String username) {
        verifyUserOwnership(id, username);
        verifyUserEnabled(username);

        if (updateUserDto.getUsername().isBlank() && updateUserDto.getEmail().isBlank()) {
            throw new InvalidUpdate();
        }

        if (!updateUserDto.getEmail().isEmpty()) {
            verifyEmailFormat(updateUserDto.getEmail());
        }

        verifyAvailability(updateUserDto.getEmail(), updateUserDto.getUsername());

        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        UserMapper.mapToUpdate(user, updateUserDto);

        userRepository.save(user);
    }

    public void updatePassword(Integer id, UpdatePasswordDto updatePasswordDto, String username) {
        verifyUserOwnership(id, username);
        verifyUserEnabled(username);

        if (updatePasswordDto.getNewPassword().isBlank() || updatePasswordDto.getOldPassword().isBlank()) {
            throw new IncorrectPasswordException();
        }

        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        if (!bCryptEncoder.matches(updatePasswordDto.getOldPassword(), user.getPassword())
                || bCryptEncoder.matches(updatePasswordDto.getNewPassword(), user.getPassword())) {
            throw new IncorrectPasswordException();
        }

        user.setPassword(bCryptEncoder.encode(updatePasswordDto.getNewPassword()));
        userRepository.save(user);
    }

    public void enableAppUser(String email) {
        userRepository.enableAppUser(email);
    }

    private void verifyAvailability(String email, String username) {
        validationService.isUsernameAvailable(username);
        validationService.isEmailAvailable(email);
    }

    private void verifyEmailFormat(String email) {
        validationService.validateEmailFormat(email);
    }

    private void verifyUserOwnership(Integer id, String username) {
        final int userId = getUserByUsername(username).getId();

        if (id == null || userId != id) {
            throw new InvalidUserIdException();
        }
    }

    private void verifyUserEnabled(String username) {
        if (!getUserWithPasswordByUsername(username).getEnabled()) {
            throw new UserNotEnabledException();
        }
    }

}
