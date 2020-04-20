package com.temp.authapp.service;

import com.temp.authapp.exception.ResourceNotFoundException;
import com.temp.authapp.model.User;
import com.temp.authapp.model.UserRequestDto;
import com.temp.authapp.model.UserResponseDto;
import com.temp.authapp.repository.UserRepository;
import com.temp.authapp.util.CustomPasswordEncoder;
import com.temp.authapp.util.ExceptionUtil;
import com.temp.authapp.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.temp.authapp.util.Constants.*;


@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomPasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailService;

    @Autowired
    private JwtUtil jwtUtilToken;


    public String generateToken(UserRequestDto userRequestDto) {
        validateUserDetails(userRequestDto);
        authenticateUser(userRequestDto);
        String token = getJwtToken(userRequestDto.getUsername());
        log.info("token {} ", token);
        return token;
    }

    private void authenticateUser(UserRequestDto userRequestDto) {
        log.info("Authenticate user : {} ", userRequestDto.getUsername());
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userRequestDto.getUsername(), userRequestDto.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(BAD_CREDENTINALS);
        }
    }

    private String getJwtToken(String username) {
        log.info("Fetch token for user : {} ", username);
        UserDetails userDetails = userDetailService.loadUserByUsername(username);
        return jwtUtilToken.generateToken(userDetails);
    }

    private void validateUserDetails(UserRequestDto userRequestDto) {
        log.info("Validating user  ", userRequestDto.getUsername());
        ExceptionUtil.validateNotEmpty(userRequestDto.getUsername(), USERNAME_EMPTY);
        ExceptionUtil.validateNotEmpty(userRequestDto.getPassword(), PASSWORD_EMPTY);
    }

    public User findByUsername(String username) {
        log.info("Fetch user by name {} ", username);
        ExceptionUtil.validateNotEmpty(username, USERNAME_EMPTY);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(INVALID_USER_NAME, username)));
        return user;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

}