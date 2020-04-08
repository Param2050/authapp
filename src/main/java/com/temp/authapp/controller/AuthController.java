package com.temp.authapp.controller;


import com.temp.authapp.model.AuthenticationRequest;
import com.temp.authapp.model.AuthenticationResponse;
import com.temp.authapp.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/v1")
public class AuthController {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailService;

    @Autowired
    private JwtUtil jwtUtilToken;

    @PostMapping("/authenticate")
    public ResponseEntity authenticate(@RequestBody AuthenticationRequest authenticationRequest)
            throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUserName(),
                            authenticationRequest.getPassword()));

        } catch (BadCredentialsException e) {
            throw new Exception("Incorect userName and Password");
        }

        final UserDetails userDetails = userDetailService.loadUserByUsername(
                authenticationRequest.getUserName());

        final String jwt = jwtUtilToken.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

}
