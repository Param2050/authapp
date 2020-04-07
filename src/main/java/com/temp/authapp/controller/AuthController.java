package com.temp.authapp.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/v1")
public class AuthController {

    @GetMapping("/")
    public ResponseEntity hello() {
        String str = "Hello this is first program for auth app";
        return ResponseEntity.ok(str);
    }

    @GetMapping("/user")
    public ResponseEntity getUser() {
        String str = "Hello this is USER";
        return ResponseEntity.ok(str);
    }

    @GetMapping("/admin")
    public ResponseEntity getAdmin() {
        String str = "Hello this is ADMIN";
        return ResponseEntity.ok(str);
    }

}
