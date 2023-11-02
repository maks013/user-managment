package com.usermanagment.user.domain;

import com.usermanagment.user.dto.UserDto;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
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

}
