package com.example.routeservice.service;

import com.example.grpc.SystemEventsRequest;
import com.example.grpc.SystemEventsResponse;
import com.example.grpc.SystemEventsServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class GrpcClientService {
    public String setAction(String action, String resource, String actionResponse) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();

        SystemEventsServiceGrpc.SystemEventsServiceBlockingStub stub =
                SystemEventsServiceGrpc.newBlockingStub(channel);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        SystemEventsResponse response = stub.getAction(SystemEventsRequest.newBuilder()
                .setTimeStamp(timestamp.toString())
                .setMicroservice("route-service")
                .setAction(action)
                .setResource(resource)
                .setResponse(actionResponse)
                .build());

        channel.shutdown();

        return response.getResponseMessage();
    }
}
