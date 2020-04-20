package com.temp.authapp.facade;


import com.temp.authapp.model.User;
import com.temp.authapp.model.UserRequestDto;
import com.temp.authapp.model.UserResponseDto;
import com.temp.authapp.service.UserService;
import com.temp.authapp.util.CustomPasswordEncoder;
import com.temp.authapp.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.temp.authapp.util.Constants.PASSWORD_EMPTY;
import static com.temp.authapp.util.Constants.USERNAME_EMPTY;

@Service
@Slf4j
public class UserFacade {

    @Autowired
    private CustomPasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    public UserResponseDto create(UserRequestDto userRequestDto) {
        validateUserDetails(userRequestDto);
        User user = createUser(userRequestDto);
        return getUserResponseDto(userService.createUser(user));
    }

    private void validateUserDetails(UserRequestDto userRequestDto) {
        log.info("Validating user  ", userRequestDto.getUsername());
        ExceptionUtil.validateNotEmpty(userRequestDto.getUsername(), USERNAME_EMPTY);
        ExceptionUtil.validateNotEmpty(userRequestDto.getPassword(), PASSWORD_EMPTY);
    }

    private User createUser(UserRequestDto userRequestDto) {
        log.info("Creating user : {} ", userRequestDto.getUsername());
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername(userRequestDto.getUsername());
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        return user;
    }

    public UserResponseDto getUserResponseDto(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setUsername(user.getUsername());
        return userResponseDto;
    }

}
