package com.temp.authapp.facade;


import com.temp.authapp.exception.DuplicateUserException;
import com.temp.authapp.model.User;
import com.temp.authapp.model.UserRequestDto;
import com.temp.authapp.model.UserResponseDto;
import com.temp.authapp.service.UserService;
import com.temp.authapp.util.CustomPasswordEncoder;
import com.temp.authapp.util.ExceptionUtil;
import com.temp.authapp.util.JwtUtil;
import com.temp.authapp.util.UserDtoUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;
import static com.temp.authapp.util.Constants.*;

@Service
@Slf4j
public class UserFacade {

    @Autowired
    private CustomPasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtilToken;

    @Autowired
    private UserDetailsService userDetailService;

    public UserResponseDto create(UserRequestDto userRequestDto) {
        validateUserDetails(userRequestDto);
        userService.validateDuplicateUser(userRequestDto.getUsername());
        User user = createUser(userRequestDto);
        return getUserResponseDto(userService.createUser(user));
    }

    public UserResponseDto getUserResponseDto(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setUsername(user.getUsername());
        return userResponseDto;
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

    public String login(UserRequestDto userRequestDto) {
        validateUserDetails(userRequestDto);
        authenticateUser(userRequestDto);
        String token = getJwtToken(userRequestDto.getUsername());
        log.info("token {} ", token);
        return token;
    }

    public void authenticateUser(UserRequestDto userRequestDto) {
        User persistedUser = userService.findByUsername(userRequestDto.getUsername());
        User baseUser = UserDtoUtils.getBaseUser(userRequestDto);
        if(!authenticate(persistedUser, baseUser)) {
            throw new BadCredentialsException(BAD_CREDENTINALS);
        }
    }

    public Boolean authenticate(User persistedUser, User baseUser) {
        return passwordEncoder.matches(baseUser.getPassword(), persistedUser.getPassword());
    }

    private String getJwtToken(String username) {
        log.info("Fetch token for user : {} ", username);
        UserDetails userDetails = userDetailService.loadUserByUsername(username);
        return jwtUtilToken.generateToken(userDetails);
    }

}
