package com.usermanagment.user.domain;

import com.usermanagment.user.dto.UserDto;
import com.usermanagment.user.dto.UserDtoWithPassword;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
class User {

    Integer id;
    String username;
    String password;
    String email;

    UserDto toDto() {
        return UserDto.builder()
                .id(id)
                .username(username)
                .email(email)
                .build();
    }

    UserDtoWithPassword toDtoWithPassword() {
        return UserDtoWithPassword.builder()
                .id(id)
                .username(username)
                .email(email)
                .password(password)
                .build();
    }
}
