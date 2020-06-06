package com.example.userservice.Controller;

import com.example.userservice.Entity.User;
import com.example.userservice.ExceptionHandling.CustomExceptions.InvalidRequestException;
import com.example.userservice.ExceptionHandling.CustomExceptions.UserNotFoundException;
import com.example.userservice.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService _userService;

    public UserController(UserService userService) {
        this._userService = userService;
    }

    @GetMapping("")
    ResponseEntity<List<User>> findAllUsers() {
        ResponseEntity<List<User>> res = _userService.findAllUsers();
        return res;
    }

    @GetMapping("/{id}")
    ResponseEntity<User> findUserById(@PathVariable(value = "id") Integer id) throws InvalidRequestException, UserNotFoundException {
        return this._userService.findUserById(id);
    }

    @PostMapping("/add")
    ResponseEntity<User> addNewUser(@RequestBody User user) throws InvalidRequestException, UserNotFoundException {
        return _userService.saveUser(user);
    }

    @PutMapping("/update/{id}")
    ResponseEntity<User> updateUser(@PathVariable(value = "id") Integer id, @RequestBody User user) throws InvalidRequestException, UserNotFoundException {
        return this._userService.updateExistingUser(id, user);
    }

    @DeleteMapping("delete/{id}")
    ResponseEntity deleteUser(@PathVariable(value = "id") Integer id) throws InvalidRequestException, UserNotFoundException {
        return this._userService.deleteUser(id);
    }

    @GetMapping(value = "", params = "email")
    public ResponseEntity<User> UserByMail(@RequestParam("email") String email) throws UserNotFoundException {
        return this._userService.findUserByEmail(email);
    }


}
