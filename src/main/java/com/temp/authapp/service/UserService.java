package com.temp.authapp.service;

import com.temp.authapp.exception.UserNotFoundException;
import com.temp.authapp.model.User;
import com.temp.authapp.model.UserDto;
import com.temp.authapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static com.temp.authapp.util.Constants.USER_NOT_FOUND;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User create(UserDto userDto) {
        User user = new User();
        user.setUserName(userDto.getUserName());
        user.setPassword(userDto.getPassword());
        user.setRoles(userDto.getRoles());
        user.setActive(userDto.isActive());
        return userRepository.save(user);
    }

    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName)
                .orElseThrow( () -> new UserNotFoundException(USER_NOT_FOUND));
    }
}
