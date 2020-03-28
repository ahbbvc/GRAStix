package com.example.userservice.Service;


import com.example.userservice.Entity.User;
import com.example.userservice.Repository.UserRepository;
import org.javatuples.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository _userRepository;
    private final ValidationService _validationService;

    UserService(UserRepository userRepository, ValidationService _validationService) {
        this._userRepository = userRepository;
        this._validationService = _validationService;
    }

    public ResponseEntity<List<User>> findAllUsers() {
        return new ResponseEntity<>(this._userRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<User> findUserById(Integer id) {
        if(!this._validationService.validateId(id)) {
            return this._validationService.processResponse(
                    null,
                    HttpStatus.BAD_REQUEST,
                    "Received Id is not valid.");
        }

        Optional<User> user = this._userRepository.findById(id);
        Pair<HttpStatus, String> validation = this._validationService.validateObject(user);
        return this._validationService.processResponse(user.orElse(null), validation.getValue0(), validation.getValue1());
    }

    public ResponseEntity<User> saveUser(User user) {
        String validation = this._validationService.validateUserProperties(user);
        if(!validation.isEmpty()) {
            return this._validationService.processResponse(
                    null,
                    HttpStatus.BAD_REQUEST,
                    String.format("Invalid request. %s", validation));
        }

        User newUser = this._userRepository.save(user);
        return this._validationService.processResponse(newUser, HttpStatus.OK, null);
    }

    public ResponseEntity<User> updateExistingUser(Integer id, User user) {
        if(!this._validationService.validateId(id)) {
            return this._validationService.processResponse(
                    null,
                    HttpStatus.BAD_REQUEST,
                    "Received Id is not valid.");
        }

        ResponseEntity<User> foundRes = this.findUserById(id);
        if(foundRes.getStatusCode() != HttpStatus.OK) return foundRes;

        user.setId(id);
        return this.saveUser(user);
    }

    public ResponseEntity deleteUser(Integer id) {
        if(!this._validationService.validateId(id)) {
            return this._validationService.processResponse(
                    null,
                    HttpStatus.BAD_REQUEST,
                    "Received Id is not valid.");
        }

        ResponseEntity<User> foundRes = this.findUserById(id);
        if(foundRes.getStatusCode() != HttpStatus.OK) return foundRes;

        this._userRepository.deleteById(id);
        return new ResponseEntity("User successfully deleted.", HttpStatus.OK);
    }
}
