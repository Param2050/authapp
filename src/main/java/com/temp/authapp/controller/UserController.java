package com.temp.authapp.controller;

import com.temp.authapp.facade.UserFacade;
import com.temp.authapp.model.AuthenticationResponse;
import com.temp.authapp.model.UserResponseDto;
import com.temp.authapp.util.ResponseData;
import com.temp.authapp.model.User;
import com.temp.authapp.model.UserRequestDto;
import com.temp.authapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserFacade userFacade;

    @PostMapping("/v1/users")
    public ResponseEntity create(@RequestBody UserRequestDto userRequestDto) {
        UserResponseDto userResponseDto = userFacade.create(userRequestDto);
        ResponseData responseData = new ResponseData(true, null, 201, userResponseDto);
        return new ResponseEntity<>(responseData, HttpStatus.CREATED);
    }

    @PostMapping("/v1/users/generate-token")
    public ResponseEntity generateToken(@RequestBody UserRequestDto userRequestDto) {
        String jwtToken = userService.generateToken(userRequestDto);
        ResponseData responseData = new ResponseData(true, null, 200,
                new AuthenticationResponse(jwtToken));
        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/v1/users/{username}")
    public ResponseEntity getUser(@PathVariable String username) {
        User user = userService.findByUsername(username);
        ResponseData responseData = new ResponseData(true, null, 200, user);
        return ResponseEntity.ok(responseData);
    }
}
