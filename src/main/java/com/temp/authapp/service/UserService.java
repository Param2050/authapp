package com.temp.authapp.service;

import com.temp.authapp.exception.DuplicateUserException;
import com.temp.authapp.exception.ResourceNotFoundException;
import com.temp.authapp.model.User;
import com.temp.authapp.repository.UserRepository;
import com.temp.authapp.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.temp.authapp.util.Constants.*;


@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(INVALID_USER_NAME, username)));
        return user;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public void validateDuplicateUser(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isPresent()) {
            throw new DuplicateUserException(DUPLICATE_USER_EXCEPTION);
        }
    }
}