package com.usermanagment.user.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UpdatePasswordDto {

    private final String oldPassword;
    private final String newPassword;

}
