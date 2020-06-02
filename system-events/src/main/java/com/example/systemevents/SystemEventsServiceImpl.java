package com.example.systemevents;

import com.example.grpc.SystemEventsRequest;
import com.example.grpc.SystemEventsResponse;
import com.example.grpc.SystemEventsServiceGrpc;
import com.example.systemevents.model.Event;
import com.example.systemevents.repository.EventRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class SystemEventsServiceImpl extends SystemEventsServiceGrpc.SystemEventsServiceImplBase {

    private final EventRepository repository;

    public SystemEventsServiceImpl(EventRepository repository) {
        this.repository = repository;
    }

    @Override
    public void getAction(SystemEventsRequest request, StreamObserver<SystemEventsResponse> responseObserver) {

        String timeStamp = request.getTimeStamp();
        String microservice = request.getMicroservice();
        String action = request.getAction();
        String resource = request.getResource();
        String responseStatus = request.getResponse();

        Event systemEvent = new Event(timeStamp, microservice, action, resource, responseStatus);
        repository.save(systemEvent);

        SystemEventsResponse response = SystemEventsResponse.newBuilder()
                .setResponseMessage(request.getResponse())
                .build();

        responseObserver.onNext(response);
        System.out.println(response);
        responseObserver.onCompleted();
    }
}
