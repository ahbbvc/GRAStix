package com.example.ticketservice.Service;

import com.example.grpc.SystemEventsRequest;
import com.example.grpc.SystemEventsResponse;
import com.example.grpc.SystemEventsServiceGrpc;
import com.example.ticketservice.Exeption.TicketNotFoundException;
import com.example.ticketservice.Model.SingleTicket;
import com.example.ticketservice.Repository.STicketRepository;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class STicketService {
    private final STicketRepository sTicketRepository;
    ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost",9090 ).usePlaintext().build();
    SystemEventsServiceGrpc.SystemEventsServiceBlockingStub stub1= SystemEventsServiceGrpc.newBlockingStub(channel);

    public STicketService(STicketRepository sTicketRepository) {
        this.sTicketRepository = sTicketRepository;
    }
    public List<SingleTicket> findAll(){
        List<SingleTicket> st=  sTicketRepository.findAll();
        Timestamp time = new Timestamp(System.currentTimeMillis());
        SystemEventsResponse systemEventResponse = stub1.getAction(SystemEventsRequest.newBuilder()
                .setMicroservice("ticket-service")
                .setAction("GET")
                .setResponse("OK")
                .setResource("SingleTicket")
                .setTimeStamp(time.toString())
                .build());
        return st;
    }
    public SingleTicket newSTicket(SingleTicket st){
        SingleTicket st1 =sTicketRepository.save(st);
        Timestamp time = new Timestamp(System.currentTimeMillis());
        SystemEventsResponse systemEventResponse = stub1.getAction(SystemEventsRequest.newBuilder()
                .setMicroservice("ticket-service")
                .setAction("POST")
                .setResponse("OK")
                .setResource("SingleTicket")
                .setTimeStamp(time.toString())
                .build());
        return st1;

    }
    public ResponseEntity<SingleTicket> findById(Integer id) throws TicketNotFoundException{
        Timestamp time = new Timestamp(System.currentTimeMillis());
        try {
            Optional<SingleTicket> st = sTicketRepository.findById(id);
            SystemEventsResponse systemEventResponse = stub1.getAction(SystemEventsRequest.newBuilder()
                    .setMicroservice("ticket-service")
                    .setAction("GET")
                    .setResponse("OK")
                    .setResource("SingleTicket")
                    .setTimeStamp(time.toString())
                    .build());
            return new ResponseEntity<SingleTicket>(st.get(), HttpStatus.OK);
        }
        catch(Exception e){
            SystemEventsResponse systemEventResponse = stub1.getAction(SystemEventsRequest.newBuilder()
                    .setMicroservice("ticket-service")
                    .setAction("GET")
                    .setResponse("ERROR")
                    .setResource("SingleTicket")
                    .setTimeStamp(time.toString())
                    .build());
            throw new TicketNotFoundException("Single ticket", id);
        }

    }
    public ResponseEntity<Object> deleteSTicket(Integer id) throws TicketNotFoundException{
        Timestamp time = new Timestamp(System.currentTimeMillis());
        try {
            ResponseEntity<SingleTicket> st = findById(id);
            sTicketRepository.deleteById(id);
            SystemEventsResponse systemEventResponse = stub1.getAction(SystemEventsRequest.newBuilder()
                    .setMicroservice("ticket-service")
                    .setAction("DELETE")
                    .setResponse("OK")
                    .setResource("SingleTicket")
                    .setTimeStamp(time.toString())
                    .build());
            return new ResponseEntity<>("{\"message\" : \"Single ticket deleted\"}", HttpStatus.OK);
        }
        catch (Exception e){
            SystemEventsResponse systemEventResponse = stub1.getAction(SystemEventsRequest.newBuilder()
                    .setMicroservice("ticket-service")
                    .setAction("DELETE")
                    .setResponse("ERROR")
                    .setResource("SingleTicket")
                    .setTimeStamp(time.toString())
                    .build());
            throw e;
        }

    }
    public List<SingleTicket> findUserSTickets(Integer userId){
        List<SingleTicket> st = sTicketRepository.findByUserId(userId);
        Timestamp time = new Timestamp(System.currentTimeMillis());
        SystemEventsResponse systemEventResponse = stub1.getAction(SystemEventsRequest.newBuilder()
                .setMicroservice("ticket-service")
                .setAction("GET")
                .setResponse("OK")
                .setResource("SingleTicket")
                .setTimeStamp(time.toString())
                .build());
        return st;
    }
    public List<SingleTicket> findSticketsByRoute (Integer routeId){

        List<SingleTicket> st =sTicketRepository.findByRouteId(routeId);
        Timestamp time = new Timestamp(System.currentTimeMillis());
        SystemEventsResponse systemEventResponse = stub1.getAction(SystemEventsRequest.newBuilder()
                .setMicroservice("ticket-service")
                .setAction("GET")
                .setResponse("OK")
                .setResource("SingleTicket")
                .setTimeStamp(time.toString())
                .build());
        return st;
    }
    public List<SingleTicket> findValidatedTickets (Boolean validated){
        Timestamp time = new Timestamp(System.currentTimeMillis());
        SystemEventsResponse systemEventResponse = stub1.getAction(SystemEventsRequest.newBuilder()
                .setMicroservice("ticket-service")
                .setAction("GET")
                .setResponse("OK")
                .setResource("SingleTicket")
                .setTimeStamp(time.toString())
                .build());
        return sTicketRepository.findByValidated(validated);
    }
    public List<SingleTicket> findValidatedUserSTickets(Integer user_id, Boolean validated){
        Timestamp time = new Timestamp(System.currentTimeMillis());
        SystemEventsResponse systemEventResponse = stub1.getAction(SystemEventsRequest.newBuilder()
                .setMicroservice("ticket-service")
                .setAction("GET")
                .setResponse("OK")
                .setResource("SingleTicket")
                .setTimeStamp(time.toString())
                .build());
        return sTicketRepository.findByUserIdAndValidated(user_id, validated);
    }
    public Optional<SingleTicket> validateSTicket(Integer id) throws TicketNotFoundException {
        try {
            ResponseEntity<SingleTicket> st = findById(id);

            Timestamp time = new Timestamp(System.currentTimeMillis());
            Optional<SingleTicket> stv =sTicketRepository.findById(id)
                    .map(singleTicket -> {
                        singleTicket.setValidated(Boolean.TRUE);
                        return sTicketRepository.save(singleTicket);
                    });
            SystemEventsResponse systemEventResponse = stub1.getAction(SystemEventsRequest.newBuilder()
                    .setMicroservice("ticket-service")
                    .setAction("PUT")
                    .setResponse("OK")
                    .setResource("SingleTicket")
                    .setTimeStamp(time.toString())
                    .build());
            return stv;
        }
        catch (Exception e)
        {
            Timestamp time = new Timestamp(System.currentTimeMillis());
            SystemEventsResponse systemEventResponse = stub1.getAction(SystemEventsRequest.newBuilder()
                .setMicroservice("ticket-service")
                .setAction("PUT")
                .setResponse("ERROR")
                .setResource("SingleTicket")
                .setTimeStamp(time.toString())
                .build());
            throw e;
        }
    }

}
