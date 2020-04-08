package com.temp.authapp.service;

import com.temp.authapp.exception.UserNotFoundException;
import com.temp.authapp.model.User;
import com.temp.authapp.model.UserDto;
import com.temp.authapp.repository.UserRepository;
import com.temp.authapp.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.temp.authapp.util.Constants.*;


@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User create(UserDto userDto) {
        validateUserDetails(userDto);
        User user = new User();
        user.setUserName(userDto.getUserName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return userRepository.save(user);
    }

    private void validateUserDetails(UserDto userDto) {
        log.info("Validating user details {} ", userDto);
        ExceptionUtil.validateNotEmpty(userDto.getUserName(), USERNAME_EMPTY);
        ExceptionUtil.validateNotEmpty(userDto.getPassword(), PASSWORD_EMPTY);
    }
}
