package com.example.userservice.ExceptionHandling.CustomExceptions;

public class InvalidRequestException extends Exception {
    public InvalidRequestException(String error) {
        super(error);
    }
}
