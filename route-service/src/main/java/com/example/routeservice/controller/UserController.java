package com.example.routeservice.controller;

import com.example.routeservice.model.User;
import com.example.routeservice.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Integer id) {
        return userService.findById(id);
    }

    @GetMapping("")
    public List<User> getAllUsers() { return userService.findAll(); }

    @PostMapping("")
    public User createUser(@Valid @RequestBody User user) {
        return userService.createUser(user.getId());
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Integer id) {
        return userService.updateUser(id);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
    }

}
