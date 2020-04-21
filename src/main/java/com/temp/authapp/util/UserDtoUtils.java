package com.temp.authapp.util;

import com.temp.authapp.model.User;
import com.temp.authapp.model.UserRequestDto;
import com.temp.authapp.model.UserResponseDto;

public class UserDtoUtils {

    public static User getBaseUser(UserRequestDto userRequestDto) {
        User user = new User();
        user.setUsername(userRequestDto.getUsername());
        user.setPassword(userRequestDto.getPassword());
        return user;
    }

    public static UserResponseDto getUserResponseDto(User user) {
        return new UserResponseDto(user.getUsername());
    }
}
