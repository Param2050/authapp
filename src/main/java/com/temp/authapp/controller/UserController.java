package com.temp.authapp.controller;

import com.temp.authapp.util.ResponseData;
import com.temp.authapp.model.User;
import com.temp.authapp.model.UserDto;
import com.temp.authapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("users/create")
    public ResponseEntity create(@RequestBody UserDto userDto) {
        User user = userService.create(userDto);
        ResponseData responseData = new ResponseData(true, null, 200, user);
        return ResponseEntity.ok(responseData);
    }
}
