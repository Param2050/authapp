package com.temp.authapp.service;

import com.temp.authapp.exception.ResourceNotFoundException;
import com.temp.authapp.model.User;
import com.temp.authapp.model.UserDto;
import com.temp.authapp.repository.UserRepository;
import com.temp.authapp.util.ExceptionUtil;
import com.temp.authapp.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailService;

    @Autowired
    private JwtUtil jwtUtilToken;


    public User create(UserDto userDto) {
        validateUserDetails(userDto);
        log.info("Creating user : {} ", userDto);
        User user = new User();
        user.setUserName(userDto.getUserName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return userRepository.save(user);
    }

    public String generateToken(UserDto userDto) {
        validateUserDetails(userDto);
        authenticateUser(userDto);
        return  getJwtToken(userDto.getUserName());
    }

    private void authenticateUser(UserDto userDto) {
        log.info("Authenticate user : {} ", userDto);
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDto.getUserName(), userDto.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(BAD_CREDENTINALS);
        }
    }

    private String getJwtToken(String userName) {
        log.info("Fetch token for user : {} ", userName);
        UserDetails userDetails = userDetailService.loadUserByUsername(userName);
        return jwtUtilToken.generateToken(userDetails);
    }

    private void validateUserDetails(UserDto userDto) {
        log.info("Validating user details {} ", userDto);
        ExceptionUtil.validateNotEmpty(userDto.getUserName(), USERNAME_EMPTY);
        ExceptionUtil.validateNotEmpty(userDto.getPassword(), PASSWORD_EMPTY);
    }

    public User getUserByName(String userName) {
        log.info("Fetch user by name {} ", userName);
        ExceptionUtil.validateNotEmpty(userName, USERNAME_EMPTY);
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new ResourceNotFoundException(INVALID_USER_NAME));
        return user;
    }
}
