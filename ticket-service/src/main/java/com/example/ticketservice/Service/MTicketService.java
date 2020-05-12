package com.example.ticketservice.Service;


import com.example.ticketservice.Exeption.TicketNotFoundException;
import com.example.ticketservice.Model.MTicketRoute;
import com.example.ticketservice.Model.MonthlyTicket;
import com.example.ticketservice.Repository.MTicketRepository;
import com.example.ticketservice.Repository.MTicketRouteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MTicketService {
    private final MTicketRepository mTicketRepository;
    private final MTicketRouteRepository mTicketRouteRepository;

    public MTicketService(MTicketRepository mTicketRepository, MTicketRouteRepository mTicketRouteRepository) {
        this.mTicketRepository = mTicketRepository;
        this.mTicketRouteRepository = mTicketRouteRepository;
    }
    public List<MonthlyTicket> findAll(){
        return mTicketRepository.findAll();
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
        return m;
    }
    public ResponseEntity<MonthlyTicket> findById(Integer id) throws Exception{
        try {
            Optional<MonthlyTicket> mt = mTicketRepository.findById(id);
            return new ResponseEntity<MonthlyTicket>(mt.get(), HttpStatus.OK);
        }
        catch (Exception e){
            throw new TicketNotFoundException("Monthly ticket", id);
        }
    }
    public List<MonthlyTicket> findUserMickets(Integer userId){
        return mTicketRepository.findByUserId(userId);
    }
    public List<MonthlyTicket> findValidatedMTickets (Boolean validated){
        return mTicketRepository.findByValidated(validated);
    }
    public List<MonthlyTicket> findValidatedUserMTickets(Integer user_id, Boolean validated){
        return mTicketRepository.findByUserIdAndValidated(user_id, validated);
    }
    public Optional<MonthlyTicket> validateMTicket(Integer id) throws Exception {
        ResponseEntity<MonthlyTicket> mticket = findById(id);
        return mTicketRepository.findById(id)
                .map(mt -> {
                    mt.setValidated(Boolean.TRUE);
                    return mTicketRepository.save(mt);
                });
    }
}
