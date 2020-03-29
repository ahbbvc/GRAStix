package com.example.userservice.ExceptionHandling.CustomExceptions;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(String error) {
        super(error);
    }
}
