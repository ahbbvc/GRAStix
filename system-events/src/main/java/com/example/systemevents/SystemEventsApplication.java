package com.example.systemevents;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import java.io.IOException;

@EnableEurekaClient
@SpringBootApplication
public class SystemEventsApplication {

	public static void main(String[] args) throws IOException, InterruptedException {
		SpringApplication.run(SystemEventsApplication.class, args);

		Server server = ServerBuilder
				.forPort(9090)
				.addService(new SystemEventsServiceImpl()).build();

		server.start();
		System.out.println("Server started");
		server.awaitTermination();
	}


}
