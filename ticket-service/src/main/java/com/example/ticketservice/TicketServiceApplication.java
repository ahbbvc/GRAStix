package com.example.ticketservice;

import com.example.ticketservice.Entity.MonthlyTicket;
import com.example.ticketservice.Entity.SingleTicket;
import com.example.ticketservice.Repository.MTicketRepository;
import com.example.ticketservice.Repository.STicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.Time;
import java.util.Date;



@SpringBootApplication
public class TicketServiceApplication {
	private static final Logger log = LoggerFactory.getLogger(TicketServiceApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(TicketServiceApplication.class, args);
	}
	@Bean
	public CommandLineRunner demo(STicketRepository srepository, MTicketRepository mrepository) {
		return (args) -> {

			SingleTicket st1 = new SingleTicket();
			MonthlyTicket mt1 = new MonthlyTicket("March");

			srepository.save(st1);
			mrepository.save(mt1);

		};
	}
}
