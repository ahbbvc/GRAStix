package com.example.routeservice.service;

import com.example.routeservice.model.User;
import com.example.routeservice.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findById(Integer id) {
        //dodati error handling
        return userRepository.findById(id).orElse(null);
    }
}
