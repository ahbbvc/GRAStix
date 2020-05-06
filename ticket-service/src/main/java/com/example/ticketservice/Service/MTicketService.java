package com.example.ticketservice.Service;


import com.example.grpc.SystemEventsRequest;
import com.example.grpc.SystemEventsResponse;
import com.example.grpc.SystemEventsServiceGrpc;
import com.example.ticketservice.Exeption.TicketNotFoundException;
import com.example.ticketservice.Model.MTicketRoute;
import com.example.ticketservice.Model.MonthlyTicket;
import com.example.ticketservice.Repository.MTicketRepository;
import com.example.ticketservice.Repository.MTicketRouteRepository;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MTicketService {
    private final MTicketRepository mTicketRepository;
    private final MTicketRouteRepository mTicketRouteRepository;
    ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost",9090 ).usePlaintext().build();
    SystemEventsServiceGrpc.SystemEventsServiceBlockingStub stub1= SystemEventsServiceGrpc.newBlockingStub(channel);

    public MTicketService(MTicketRepository mTicketRepository, MTicketRouteRepository mTicketRouteRepository) {
        this.mTicketRepository = mTicketRepository;
        this.mTicketRouteRepository = mTicketRouteRepository;
    }
    public List<MonthlyTicket> findAll(){
        List<MonthlyTicket> mt = mTicketRepository.findAll();
        Timestamp time = new Timestamp(System.currentTimeMillis());
        SystemEventsResponse systemEventResponse = stub1.getAction(SystemEventsRequest.newBuilder()
                .setMicroservice("ticket-service")
                .setAction("GET")
                .setResponse("OK")
                .setResource("MonthlyTicket")
                .setTimeStamp(time.toString())
                .build());
        return mt;
    }
    public MonthlyTicket newMTicket(Integer userId, List<Integer> routes, String month){
        MonthlyTicket mt1 = new MonthlyTicket(userId , month);
        List<MTicketRoute> mTicketRoutes = new ArrayList<MTicketRoute>();
        for (Integer routeId: routes) {
            MTicketRoute mr = new MTicketRoute(routeId, mt1);
            mTicketRoutes.add(mr);
        }
        mt1.setRoutes(mTicketRoutes);
        MonthlyTicket m = mTicketRepository.save(mt1);
        for (MTicketRoute mt : mTicketRoutes){
            mTicketRouteRepository.save(mt);
        }
        Timestamp time = new Timestamp(System.currentTimeMillis());
        SystemEventsResponse systemEventResponse = stub1.getAction(SystemEventsRequest.newBuilder()
                .setMicroservice("ticket-service")
                .setAction("POST")
                .setResponse("OK")
                .setResource("MonthlyTicket")
                .setTimeStamp(time.toString())
                .build());
        return m;
    }
    public ResponseEntity<MonthlyTicket> findById(Integer id) throws Exception{
        try {
            Optional<MonthlyTicket> mt = mTicketRepository.findById(id);
            Timestamp time = new Timestamp(System.currentTimeMillis());
            SystemEventsResponse systemEventResponse = stub1.getAction(SystemEventsRequest.newBuilder()
                    .setMicroservice("ticket-service")
                    .setAction("GET")
                    .setResponse("OK")
                    .setResource("MonthlyTicket")
                    .setTimeStamp(time.toString())
                    .build());
            return new ResponseEntity<MonthlyTicket>(mt.get(), HttpStatus.OK);
        }
        catch (Exception e){
            Timestamp time = new Timestamp(System.currentTimeMillis());
            SystemEventsResponse systemEventResponse = stub1.getAction(SystemEventsRequest.newBuilder()
                    .setMicroservice("ticket-service")
                    .setAction("GET")
                    .setResponse("ERROR")
                    .setResource("MonthlyTicket")
                    .setTimeStamp(time.toString())
                    .build());
            throw new TicketNotFoundException("Monthly ticket", id);
        }
    }
    public List<MonthlyTicket> findUserMickets(Integer userId){
        List<MonthlyTicket> mt = mTicketRepository.findByUserId(userId);
        Timestamp time = new Timestamp(System.currentTimeMillis());
        SystemEventsResponse systemEventResponse = stub1.getAction(SystemEventsRequest.newBuilder()
                .setMicroservice("ticket-service")
                .setAction("GET")
                .setResponse("OK")
                .setResource("MonthlyTicket")
                .setTimeStamp(time.toString())
                .build());
        return mt;
    }
    public List<MonthlyTicket> findValidatedMTickets (Boolean validated){
        List<MonthlyTicket> mt = mTicketRepository.findByValidated(validated);
        Timestamp time = new Timestamp(System.currentTimeMillis());
        SystemEventsResponse systemEventResponse = stub1.getAction(SystemEventsRequest.newBuilder()
                .setMicroservice("ticket-service")
                .setAction("GET")
                .setResponse("OK")
                .setResource("MonthlyTicket")
                .setTimeStamp(time.toString())
                .build());
        return  mt;
    }
    public List<MonthlyTicket> findValidatedUserMTickets(Integer user_id, Boolean validated){
        List<MonthlyTicket> mt =mTicketRepository.findByUserIdAndValidated(user_id, validated);
        Timestamp time = new Timestamp(System.currentTimeMillis());
        SystemEventsResponse systemEventResponse = stub1.getAction(SystemEventsRequest.newBuilder()
                .setMicroservice("ticket-service")
                .setAction("GET")
                .setResponse("OK")
                .setResource("MonthlyTicket")
                .setTimeStamp(time.toString())
                .build());
        return mt;
    }
    public Optional<MonthlyTicket> validateMTicket(Integer id) throws Exception {
        ResponseEntity<MonthlyTicket> mticket = findById(id);
        Optional<MonthlyTicket> mt1 = mTicketRepository.findById(id)
                .map(mt -> {
                    mt.setValidated(Boolean.TRUE);
                    return mTicketRepository.save(mt);
                });
        Timestamp time = new Timestamp(System.currentTimeMillis());
        SystemEventsResponse systemEventResponse = stub1.getAction(SystemEventsRequest.newBuilder()
                .setMicroservice("ticket-service")
                .setAction("PUT")
                .setResponse("OK")
                .setResource("MonthlyTicket")
                .setTimeStamp(time.toString())
                .build());
        return mt1;
    }
}
