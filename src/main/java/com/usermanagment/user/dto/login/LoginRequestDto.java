package com.usermanagment.user.dto.login;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Builder
@Getter
public class LoginRequestDto {

    @NotBlank(message = "username can not be blank")
    private final String username;
    @NotBlank(message = "password can not be blank")
    private final String password;

}
