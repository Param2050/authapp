package com.temp.authapp.controller;

import com.temp.authapp.exception.ResourceNotFoundException;
import com.temp.authapp.exception.ValidationException;
import com.temp.authapp.util.ResponseData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public final ResponseEntity handleUserNotFoundException(UsernameNotFoundException ex) {
        ResponseData responseData = new ResponseData(false, ex.getMessage(), 400, null);
        return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity handleResourceNotFoundException(ResourceNotFoundException ex) {
        ResponseData responseData = new ResponseData(false, ex.getMessage(), 400, null);
        return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationException.class)
    public final ResponseEntity handleValidationException(ValidationException ex) {
        ResponseData responseData = new ResponseData(false, ex.getMessage(), 400, null);
        return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity handleGeneralException(Exception ex) {
        ResponseData responseData = new ResponseData(false, ex.getMessage(), 400, null);
        return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public final ResponseEntity handleBadCredentialsException(Exception ex) {
        ResponseData responseData = new ResponseData(false, ex.getMessage(), 400, null);
        return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
    }
}