package com.example.routeservice.service;

import com.example.routeservice.model.User;
import com.example.routeservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() { return userRepository.findAll(); }

    public User findById(Integer id) {
        return userRepository.findById(id).orElseThrow();
    }

    public User createUser(Integer id) {
        User newUser = new User(id);
        return userRepository.save(newUser);
    }

    public User updateUser(Integer id) {
        User user = userRepository.findById(id).orElseThrow();
        user.setId(id);
        return userRepository.save(user);
    }

    public void deleteUser(Integer id) {
        User user = userRepository.findById(id).orElseThrow();
        userRepository.deleteById(id);
    }
}
