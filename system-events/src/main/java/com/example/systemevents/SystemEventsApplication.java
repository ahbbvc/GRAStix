package com.example.systemevents;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class SystemEventsApplication {

	public static void main(String[] args) throws IOException, InterruptedException {
		SpringApplication.run(SystemEventsApplication.class, args);
		Server server = ServerBuilder
				.forPort(8080)
				.addService(new SystemEventsImpl())
				.build();

		server.start();
		server.awaitTermination();
	}

}
