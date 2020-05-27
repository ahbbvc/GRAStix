package com.example.userservice.Service;

import com.example.userservice.Entity.User;
import com.example.userservice.ExceptionHandling.CustomExceptions.InvalidRequestException;
import com.example.userservice.ExceptionHandling.CustomExceptions.UserNotFoundException;
import com.sun.istack.Nullable;
import org.javatuples.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ValidationService {
    public ValidationService() {}

    public void validateId(Integer id) throws InvalidRequestException {
        if(id == null || id < 1) {
            throw new InvalidRequestException("Received Id is not valid.");
        }
    }

    public <T> void validateObject(Optional<T> entity) throws UserNotFoundException {
        if(!entity.isPresent()) {
            throw new UserNotFoundException("User with received ID was not found.");
        }
    }

    public void validateUserProperties(User user) throws InvalidRequestException {
        List<String> nullProperties = new ArrayList<String>();
        List<String> invalidProperties = new ArrayList<String>();

        if(user.getFirstName() == null || user.getFirstName().isEmpty())  {
            nullProperties.add("First Name");
        } else if(!user.getFirstName().toLowerCase().matches("^[a-zA-Z]+( ?[a-zA-Z])*$")) {
            invalidProperties.add("First Name");
        }

        if(user.getLastName() == null || user.getLastName().isEmpty()) {
            nullProperties.add("Last Name");
        } else if(!user.getLastName().matches("^[a-zA-Z]+( ?[a-zA-Z])*$")) {
            invalidProperties.add("Last Name");
        }

        if(user.getBirthDate() == null) {
            nullProperties.add("Birth Date");
        } else if(user.getBirthDate().after(new Date())) {
            invalidProperties.add("Birth date");
        }

        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            nullProperties.add("Email");
        } else if(!user.getEmail().matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")){
            invalidProperties.add("Email");
        }

        if(user.getPassword() == null || user.getPassword().isEmpty()) nullProperties.add("Password");

        if(user.getStatus() != null &&
           !user.getStatus().isEmpty() &&
           !user.getStatus().toLowerCase().matches("^(student|penzioner|radnik|admin)?$")) {
            invalidProperties.add("Status should be equal to one of the following: Student, Penzioner, Radnik, Admin");
        }

        String result = nullProperties.size() > 0 ?
                "Properties that must be provided: " + String.join(", ", nullProperties) + ". " :
                "";

        if(invalidProperties.size() > 0) {
            result += "Wrong format: " + String.join(", ", invalidProperties) + ".";
        }

        if(!result.isEmpty()) {
            throw new InvalidRequestException(result);
        }
    }
}
