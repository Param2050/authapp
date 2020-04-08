package com.temp.authapp.service;


import com.temp.authapp.model.UserDto;
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

import static com.temp.authapp.util.Constants.*;

@Service
@Slf4j
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailService;

    @Autowired
    private JwtUtil jwtUtilToken;

    public String authenticate(UserDto userDto) {
        validateUserDetails(userDto);
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDto.getUserName(), userDto.getPassword()));

        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(BAD_CREDENTINALS);
        }

        UserDetails userDetails = userDetailService.loadUserByUsername(userDto.getUserName());
        return jwtUtilToken.generateToken(userDetails);
    }

    private void validateUserDetails(UserDto userDto) {
        log.info("Validating user details {} ", userDto);
        ExceptionUtil.validateNotEmpty(userDto.getUserName(), USERNAME_EMPTY);
        ExceptionUtil.validateNotEmpty(userDto.getPassword(), PASSWORD_EMPTY);
    }

}
