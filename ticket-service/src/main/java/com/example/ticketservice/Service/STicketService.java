package com.example.ticketservice.Service;

import com.example.ticketservice.Exeption.TicketNotFoundException;
import com.example.ticketservice.Model.SingleTicket;
import com.example.ticketservice.Repository.STicketRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class STicketService {
    private final STicketRepository sTicketRepository;


    public STicketService(STicketRepository sTicketRepository) {
        this.sTicketRepository = sTicketRepository;
    }

    public List<SingleTicket> findAll() {
        return sTicketRepository.findAll();
    }

    public SingleTicket newSTicket(SingleTicket st) {
        return sTicketRepository.save(st);
    }

    public ResponseEntity<SingleTicket> findById(Integer id) throws TicketNotFoundException {
        try {
            Optional<SingleTicket> st = sTicketRepository.findById(id);
            return new ResponseEntity<SingleTicket>(st.get(), HttpStatus.OK);
        } catch (Exception e) {
            throw new TicketNotFoundException("Single ticket", id);
        }

    }

    public ResponseEntity<Object> deleteSTicket(Integer id) throws TicketNotFoundException {
        try {
            ResponseEntity<SingleTicket> st = findById(id);
            sTicketRepository.deleteById(id);
            return new ResponseEntity<>("{\"message\" : \"Single ticket deleted\"}", HttpStatus.OK);
        } catch (Exception e) {
            throw e;
        }

    }

    public List<SingleTicket> findUserSTickets(Integer userId) {
        return sTicketRepository.findByUserId(userId);
    }

    public List<SingleTicket> findSticketsByRoute(Integer routeId) {
        return sTicketRepository.findByRouteId(routeId);
    }

    public List<SingleTicket> findValidatedTickets(Boolean validated) {
        return sTicketRepository.findByValidated(validated);
    }

    public List<SingleTicket> findValidatedUserSTickets(Integer user_id, Boolean validated) {
        return sTicketRepository.findByUserIdAndValidated(user_id, validated);
    }

    public Optional<SingleTicket> validateSTicket(Integer id) throws TicketNotFoundException {
        try {
            ResponseEntity<SingleTicket> st = findById(id);
            return sTicketRepository.findById(id)
                    .map(singleTicket -> {
                        singleTicket.setValidated(Boolean.TRUE);
                        return sTicketRepository.save(singleTicket);
                    });
        } catch (Exception e) {
            throw e;
        }
    }

}
