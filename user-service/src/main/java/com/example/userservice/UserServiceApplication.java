package com.example.userservice;

import com.example.userservice.Entity.Korisnik;
import com.example.userservice.Repository.KorisnikRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}


	@Bean
	public CommandLineRunner demo(KorisnikRepository kr) {
		return (args) -> {
			kr.save(new Korisnik("Amra", "Habibovic", new Date(1997, 1, 4), "ahabibovic1@etf.unsa.ba",
					"abcd", "123", "123", new Date(2022, 1, 1), "student"));

		};
	}

}
