package com.temp.authapp.service;

import com.temp.authapp.exception.DuplicateUserException;
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

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static com.temp.authapp.util.Constants.*;


@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

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

    public void validateDuplicateUser(UserRequestDto userRequestDto) {
        Optional<User> user = userRepository.findByUsername(userRequestDto.getUsername());
        if(user.isPresent()) {
            throw new DuplicateUserException(DUPLICATE_USER_EXCEPTION);
        }
    }
}