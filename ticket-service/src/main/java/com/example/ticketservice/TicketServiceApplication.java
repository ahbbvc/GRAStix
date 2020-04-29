package com.example.ticketservice;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.example.grpc.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
@EnableEurekaClient
public class TicketServiceApplication {




	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	public static void main(String[] args) {
		ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost",8080 ).usePlaintext().build();
		SystemEventsServiceGrpc.SystemEventsServiceBlockingStub stub1= SystemEventsServiceGrpc.newBlockingStub(channel);
		SystemEventResponse systemEventResponse = stub1.add(SystemEventsRequest.newBuilder()
				.setMicroservice("ticket-service")
				.setAction("GET")
				.setResponse("OK")
				.setResurs("SingleTicket")
				.setTimeStamp("SAd")
				.build());
		System.out.println(systemEventResponse);
		channel.shutdown();
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
/*@RestController
class ServiceInstanceRestController {

	@Autowired
	private DiscoveryClient discoveryClient;

	@RequestMapping("/service-instances/{applicationName}")
	public List<ServiceInstance> serviceInstancesByApplicationName(
			@PathVariable String applicationName) {
		return this.discoveryClient.getInstances(applicationName);
	}
}*/