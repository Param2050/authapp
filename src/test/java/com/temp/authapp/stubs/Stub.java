package com.temp.authapp.stubs;

import com.temp.authapp.model.User;
import com.temp.authapp.model.UserResponseDto;
import com.temp.authapp.util.CustomPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;

public class Stub {

    @Autowired
    private CustomPasswordEncoder customPasswordEncoder;

    public static UserResponseDto expectedResponse(User user) {
        return new UserResponseDto(user.getUsername());
    }
}
