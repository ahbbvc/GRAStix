package com.example.userservice;

import com.example.userservice.Entity.User;
import com.example.userservice.ExceptionHandling.CustomExceptions.InvalidRequestException;
import com.example.userservice.ExceptionHandling.CustomExceptions.UserNotFoundException;
import com.example.userservice.Service.ValidationService;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ValidationServiceTests {

    private ValidationService validationService;

    ValidationServiceTests() {
        validationService = new ValidationService();
    }

    @Test
    public void validateId_ShouldThrowInvalidRequestException() {
        Exception exception = assertThrows(InvalidRequestException.class, () -> {
           validationService.validateId(0);
        });

        String expectedMessage = "Received Id is not valid.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void validateId_ShouldNotThrowException_WhenIdIsValid() {
        assertDoesNotThrow(() -> {
            validationService.validateId(2);
        });
    }

    @Test
    public void validateObject_ShouldThrowUserNotFoundException() {
        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            validationService.validateObject(Optional.empty());
        });

        String expectedMessage = "User with received ID was not found.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void validateObject_ShouldNotThrowException_WhenObjectIsNotNull() {
        User user = new User(null, null, new Date(), "email@email.com", "password", "123456",
                "123", new Date(), "");
        assertDoesNotThrow(() -> {
            validationService.validateObject(Optional.of(user));
        });
    }

    //First case - missing required properties (e.g. first and last name, email)
    @Test
    public void validateUserProperties_ShouldThrowInvalidRequestException_1() {
        User user = new User(null, null, new Date(), "", "password", "123456",
                "123", new Date(), "");

        Exception exception = assertThrows(InvalidRequestException.class, () -> {
            validationService.validateUserProperties(user);
        });

        String expectedMessage = "Properties that must be provided: First Name, Last Name, Email.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    //Second case - wrong format
    @Test
    public void validateUserProperties_ShouldThrowInvalidRequestException_2() {
        User user = new User("Test", "User", new Date(), "wrong format", "password", "123456",
                "123", new Date(), "wrong format");

        Exception exception = assertThrows(InvalidRequestException.class, () -> {
            validationService.validateUserProperties(user);
        });

        String expectedMessage = "Wrong format: Email, Status should be equal to one of the following: Student, Penzioner, Radnik";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void validateUserProperties_ShouldNotThrowException_WhenUserIsValid() {
        User user = new User("Test", "User", new Date(), "email@email.com", "password", "123456",
                "123", new Date(), "Student");

        assertDoesNotThrow(() -> {
            validationService.validateUserProperties(user);
        });
    }
}
