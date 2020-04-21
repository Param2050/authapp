package com.temp.authapp.exception;

public class DuplicateUserException extends RuntimeException {
    public DuplicateUserException() {super();}
    public DuplicateUserException(String message) { super(message);}
    public DuplicateUserException(String message, Throwable cause) { super(message, cause);}
}
