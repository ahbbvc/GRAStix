package com.example.userservice.Service;

import com.example.userservice.Entity.User;
import com.sun.istack.Nullable;
import org.javatuples.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ValidationService {
    ValidationService() {}

    public boolean validateId(Integer id) {
        return id != null && id > 0;
    }

    public <T> Pair validateObject(Optional<T> entity) {
        return entity.isPresent() ?
                new Pair(HttpStatus.OK, null):
                new Pair(HttpStatus.NOT_FOUND, "User with received ID was not found.");
    }

    public String validateUserProperties(User user) {
        List<String> nullProperties = new ArrayList<String>();
        List<String> invalidProperties = new ArrayList<String>();

        if(user.getFirstName() == null || user.getFirstName().isEmpty()) nullProperties.add("First Name");
        if(user.getLastName() == null || user.getLastName().isEmpty()) nullProperties.add("Last Name");
        if(user.getBirthDate() == null) nullProperties.add("Birth Date");
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            nullProperties.add("Email");
        } else if(!user.getEmail().matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")){
            invalidProperties.add("Email");
        }
        if(user.getPassword() == null || user.getPassword().isEmpty()) nullProperties.add("Password");

        String result = nullProperties.size() > 0 ?
                "Properties that must be provided: " + String.join(", ", nullProperties) + ". " :
                "";

        if(invalidProperties.size() > 0) {
            result += "Wrong format: " + String.join(", ", invalidProperties);
        }

        return result;
    }

    public <T> ResponseEntity<T> processResponse(@Nullable T entity, HttpStatus httpStatus, @Nullable  String error) {
        return entity != null ? new ResponseEntity<T>(entity, httpStatus) : new ResponseEntity(error, httpStatus);
    }
}
