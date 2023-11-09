package com.usermanagment.user.dto;


import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RegistrationRequest {

    private final String username;
    private final String password;
    private final String email;

}
