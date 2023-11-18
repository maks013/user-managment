package com.usermanagment.user.infrastructure;

import com.usermanagment.confirmationtoken.domain.TokenFacade;
import com.usermanagment.user.domain.UserFacade;
import com.usermanagment.user.dto.UpdatePasswordDto;
import com.usermanagment.user.dto.UpdateUserDto;
import com.usermanagment.user.dto.UserDto;
import com.usermanagment.user.dto.UserDtoWithPassword;
import com.usermanagment.user.exception.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserFacade userFacade;
    private final TokenFacade tokenFacade;

    @GetMapping
    public ResponseEntity<List<UserDto>> readAllUsers() {
        return ResponseEntity.ok(userFacade.readAllUsers());
    }

    @GetMapping("/user")
    public ResponseEntity<UserDtoWithPassword> readUser(Principal principal) {
        UserDtoWithPassword userDtoWithPassword = userFacade.getUserWithPasswordByUsername(principal.getName());
        return ResponseEntity.ok(userDtoWithPassword);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id,
                                        Principal principal) {
        try {
            tokenFacade.deleteToken(id);
            userFacade.deleteUser(id, principal.getName());
            return ResponseEntity.noContent().build();
        } catch (UserNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (InvalidUserIdException exception) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUserData(@PathVariable Integer id,
                                            @RequestBody UpdateUserDto updateUserDto,
                                            Principal principal) {
        try {
            userFacade.updateUser(id, updateUserDto, principal.getName());
            return ResponseEntity.ok().build();
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (TakenEmailException | TakenUsernameException |
                InvalidEmailFormatException | InvalidUserIdException | InvalidUpdate exception) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        } catch (UserNotEnabledException exception){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PutMapping("/update-password/{id}")
    public ResponseEntity<?> updateUserPassword(@PathVariable Integer id,
                                                @RequestBody UpdatePasswordDto updatePasswordDto,
                                                Principal principal) {
        try {
            userFacade.updatePassword(id, updatePasswordDto, principal.getName());
            return ResponseEntity.ok().build();
        } catch (UserNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IncorrectPasswordException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
