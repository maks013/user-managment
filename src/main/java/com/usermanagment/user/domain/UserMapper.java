package com.usermanagment.user.domain;

import com.usermanagment.user.dto.UpdateUserDto;

class UserMapper {

    static void mapToUpdate(User user, UpdateUserDto updateUserDto) {
        user.setUsername(updateUserDto.getUsername().isEmpty()
                ? user.getUsername() : updateUserDto.getUsername());
        user.setEmail(updateUserDto.getEmail().isEmpty()
                ? user.getEmail() : updateUserDto.getEmail());
    }
}
