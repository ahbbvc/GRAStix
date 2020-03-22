package com.example.routeservice;

import com.example.routeservice.Entity.Route;
import com.example.routeservice.Entity.RouteStation;
import com.example.routeservice.Entity.Station;
import com.example.routeservice.Entity.TimeTable;
import com.example.routeservice.Repository.RouteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;

@SpringBootApplication
public class RouteServiceApplication {
	private static final Logger log = LoggerFactory.getLogger(RouteServiceApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(RouteServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(RouteRepository repository) {
		return (args) -> {
			// save a few routes
			/*
			Route r1 = new Route("Vijecnica-Dobrinja", "Autobus");
			Route r2 = new Route("Bentbasa-Aerodrom", "Autobus");
			repository.save(r1);
			repository.save(r2);
			 */
		};
	}
}