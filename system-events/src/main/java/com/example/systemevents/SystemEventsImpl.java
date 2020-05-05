package com.example.systemevents;

import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.grpc.SystemEventResponse;
import org.example.grpc.SystemEventsRequest;
import org.example.grpc.SystemEventsServiceGrpc;

public class SystemEventsImpl extends SystemEventsServiceGrpc.SystemEventsServiceImplBase {
    private Logger logger = LogManager.getLogger(getClass());


    @Override
    public void add(SystemEventsRequest request, StreamObserver<SystemEventResponse> responseObserver) {
        logger.info(String.format(
                "New event received: %s %s %s %s %s",
                request.getTimeStamp(),
                request.getMicroservice(),
                request.getAction(),
                request.getResurs(),
                request.getResponse()
        ));

        SystemEventResponse response = SystemEventResponse.newBuilder()
                .setSucc("Success")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
