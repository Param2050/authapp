package com.temp.authapp.controller;


import com.temp.authapp.model.AuthenticationResponse;
import com.temp.authapp.util.ResponseData;
import com.temp.authapp.model.UserDto;
import com.temp.authapp.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/authenticate")
    public ResponseEntity authenticate(@RequestBody UserDto userDto) {
        String jwtToken = authService.authenticate(userDto);
        ResponseData responseData = new ResponseData(true, null, 200,
                new AuthenticationResponse(jwtToken));
        return ResponseEntity.ok(responseData);
    }

}
