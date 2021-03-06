package com.temp.authapp.service;

import com.temp.authapp.model.CustomUserDetails;
import com.temp.authapp.model.User;
import com.temp.authapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.temp.authapp.util.Constants.INVALID_USER_NAME;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(INVALID_USER_NAME, username)));
        return new CustomUserDetails(user);
    }
}
