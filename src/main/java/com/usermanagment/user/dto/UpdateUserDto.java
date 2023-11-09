package com.usermanagment.user.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UpdateUserDto {

    private final String username;
    private final String email;

}
