package com.example.systemevents;

import com.example.systemevents.SystemEventsServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GrpcServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder
                .forPort(9090)
                .addService(new SystemEventsServiceImpl()).build();

        server.start();
        System.out.println("Server started");
        server.awaitTermination();
    }
}

