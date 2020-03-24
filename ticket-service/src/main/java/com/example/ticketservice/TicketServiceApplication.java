package com.example.ticketservice;

import com.example.ticketservice.Entity.MTicketRoute;
import com.example.ticketservice.Entity.MonthlyTicket;
import com.example.ticketservice.Entity.SingleTicket;
import com.example.ticketservice.Repository.MTicketRepository;
import com.example.ticketservice.Repository.MTicketRouteRepository;
import com.example.ticketservice.Repository.STicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@SpringBootApplication
public class TicketServiceApplication {
	private static final Logger log = LoggerFactory.getLogger(TicketServiceApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(TicketServiceApplication.class, args);
	}
	@Bean
	public CommandLineRunner demo(STicketRepository srepository, MTicketRepository mrepository, MTicketRouteRepository mrrepository) {
		return (args) -> {

		/*	SingleTicket st1 = new SingleTicket(1, 1 );


			MonthlyTicket mt1 = new MonthlyTicket(1 , "March");
			MTicketRoute mr1 = new MTicketRoute(1,mt1 );
			MTicketRoute mr2 = new MTicketRoute(2, mt1);
			List<MTicketRoute> mTicketRoutes = new ArrayList<MTicketRoute>();
			mTicketRoutes.add(mr1);
			mTicketRoutes.add(mr2);
			mt1.setRoutes(mTicketRoutes);
			srepository.save(st1);
			mrepository.save(mt1);
			mrrepository.save(mr1);
			mrrepository.save(mr2);*/

		};
	}
}
