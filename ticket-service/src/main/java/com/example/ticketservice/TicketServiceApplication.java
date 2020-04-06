package com.example.ticketservice;

import com.example.ticketservice.Repository.MTicketRepository;
import com.example.ticketservice.Repository.MTicketRouteRepository;
import com.example.ticketservice.Repository.STicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@SpringBootApplication
@EnableEurekaClient
public class TicketServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(TicketServiceApplication.class, args);
	}
	/*@Bean
	public CommandLineRunner demo(STicketRepository srepository, MTicketRepository mrepository, MTicketRouteRepository mrrepository) {
		return (args) -> {

		//	SingleTicket st1 = new SingleTicket(1, 1 );
		//	srepository.save(st1);

			/*MonthlyTicket mt1 = new MonthlyTicket(2 , "March");
			MTicketRoute mr1 = new MTicketRoute(1,mt1 );
			MTicketRoute mr2 = new MTicketRoute(2, mt1);
			List<MTicketRoute> mTicketRoutes = new ArrayList<MTicketRoute>();
			mTicketRoutes.add(mr1);
			mTicketRoutes.add(mr2);
			mt1.setRoutes(mTicketRoutes);

			mrepository.save(mt1);
			mrrepository.save(mr1);
			mrrepository.save(mr2);

		};
	}*/
}
@RestController
class ServiceInstanceRestController {

	@Autowired
	private DiscoveryClient discoveryClient;

	@RequestMapping("/service-instances/{applicationName}")
	public List<ServiceInstance> serviceInstancesByApplicationName(
			@PathVariable String applicationName) {
		return this.discoveryClient.getInstances(applicationName);
	}
}