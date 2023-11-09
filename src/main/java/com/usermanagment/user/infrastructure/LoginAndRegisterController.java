package com.usermanagment.user.infrastructure;

import com.usermanagment.confirmationtoken.domain.TokenFacade;
import com.usermanagment.infrastructure.email.EmailBuilder;
import com.usermanagment.infrastructure.email.EmailSender;
import com.usermanagment.infrastructure.jwt.JwtAuthenticator;
import com.usermanagment.user.domain.UserFacade;
import com.usermanagment.user.dto.RegistrationRequest;
import com.usermanagment.user.dto.UserDto;
import com.usermanagment.user.dto.login.LoginRequestDto;
import com.usermanagment.user.dto.login.LoginResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class LoginAndRegisterController {

    private final JwtAuthenticator jwtAuthenticator;
    private final UserFacade userFacade;
    private final EmailSender emailSender;
    private final TokenFacade tokenFacade;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        final LoginResponseDto loginResponseDto = jwtAuthenticator.authenticate(loginRequestDto);
        return ResponseEntity.ok(loginResponseDto);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody RegistrationRequest registrationRequest) {
        UserDto userDto = userFacade.registerUser(registrationRequest);
        final String token = tokenFacade.createTokenLink(registrationRequest.getEmail());
        emailSender.send(
                registrationRequest.getEmail(),
                EmailBuilder.buildEmail(registrationRequest.getUsername(), token)
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }
}
