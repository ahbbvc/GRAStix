package com.example.userservice;

import com.example.userservice.Entity.User;
import com.example.userservice.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class UserServiceApplication {

	private static final Logger log = LoggerFactory.getLogger(UserServiceApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(UserRepository repository) {
		return (args) -> {
			// save a few users
			repository.save(new User("Amra", "Habibovic", new Date(1994, 1, 4),
					"ahabibovic1@etf.unsa.ba", "12345", "123", "123",
					new Date(2022, 1,1), "student"));
		};
	}
}
