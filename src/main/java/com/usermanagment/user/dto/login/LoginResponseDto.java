package com.usermanagment.user.dto.login;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoginResponseDto {

    private final String username;
    private final String token;

}
