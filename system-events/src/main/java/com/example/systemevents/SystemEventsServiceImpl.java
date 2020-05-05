package com.example.systemevents;

import com.example.grpc.SystemEventsRequest;
import com.example.grpc.SystemEventsResponse;
import com.example.grpc.SystemEventsServiceGrpc;
import io.grpc.stub.StreamObserver;

public class SystemEventsServiceImpl extends SystemEventsServiceGrpc.SystemEventsServiceImplBase  {

    @Override
    public void getAction(SystemEventsRequest request, StreamObserver<SystemEventsResponse> responseObserver) {

        SystemEventsResponse response = SystemEventsResponse.newBuilder()
                .setResponseMessage(request.getResponse())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
