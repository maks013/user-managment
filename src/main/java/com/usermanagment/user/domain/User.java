package com.usermanagment.user.domain;

import com.usermanagment.user.dto.UserDto;
import com.usermanagment.user.dto.UserDtoWithPassword;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
