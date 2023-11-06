package com.usermanagment.user.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserDtoWithPassword {

    private Integer id;
    private String username;
    private String password;
    private String email;

}
