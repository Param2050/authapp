package com.temp.authapp.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class AuthController {

    @GetMapping("/hello")
    public ResponseEntity helloAuthApp() {
        String str = "Hello this is first program for auth app";
        return ResponseEntity.ok(str);
    }
}
