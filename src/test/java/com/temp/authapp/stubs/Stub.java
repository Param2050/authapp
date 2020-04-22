package com.temp.authapp.stubs;

import com.temp.authapp.model.User;
import com.temp.authapp.model.UserResponseDto;
import com.temp.authapp.util.CustomPasswordEncoder;
import java.util.UUID;


public class Stub {

    public static final String USERNAME = "Rohit";
    public static final String PASSWORD = "rohit123";
    public static final String EMPTY_USERNAME = "";
    public static final String MISMATCH_USERNAME = "blah";
    public static final String MISMATCH_PASSWORD = "blah123";


    public static User getUser(User user) {
        user.setId(UUID.randomUUID());
        user.setUsername(USERNAME);
        user.setPassword(new CustomPasswordEncoder().encode(PASSWORD));
        return user;
    }

    public static UserResponseDto expectedResponse(User user) {
        return new UserResponseDto(user.getUsername());
    }
}
