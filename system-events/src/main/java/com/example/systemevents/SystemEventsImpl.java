package com.example.systemevents;

import io.grpc.stub.StreamObserver;
import org.example.grpc.SystemEventResponse;
import org.example.grpc.SystemEventsRequest;
import org.example.grpc.SystemEventsServiceGrpc;

public class SystemEventsImpl extends SystemEventsServiceGrpc.SystemEventsServiceImplBase {
   //@Autowired
    //EventService eventService;

    @Override
    public void add(SystemEventsRequest request, StreamObserver<SystemEventResponse> responseObserver) {

        String succ = new StringBuilder()
                .append("Dodan ")
                .append(request.getMicroservice())
                .toString();
        SystemEventResponse response= SystemEventResponse.newBuilder()
                .setSucc(succ)
                .build();
       // Event e = new Event(request.getMicroservice(), request.getAction(), request.getResurs(), request.getResponse());
        //eventService.newEvent(e);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
