package com.example.ticketservice.Controller;

import com.example.ticketservice.Entity.SingleTicket;
import com.example.ticketservice.Repository.STicketRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
public class STicketController {
    private final STicketRepository repository;
    STicketController(STicketRepository repository) {
        this.repository = repository;
    }
    @GetMapping("/single_tickets")
    List<SingleTicket> AllSTickets(){
        return repository.findAll();
    }

    @PostMapping("/single_tickets")
    SingleTicket newSTicket(@RequestBody SingleTicket st){
        return repository.save(st);
    }

    @GetMapping("/single_tickets/{id}")
    Optional<SingleTicket> STicketById(@PathVariable Integer id){
        return repository.findById(id);
    }

    @GetMapping(value = "/single_tickets", params = "user_id")
    List<SingleTicket> UserSTickets(@RequestParam("user_id") Integer userId){
        return repository.findByUserId(userId);
    }

    @GetMapping(value = "/single_tickets", params = "route_id")
    List<SingleTicket> SticketsByRoute(@RequestParam("route_id") Integer routeId){
        return repository.findByRouteId(routeId);
    }

    @GetMapping(value = "/single_tickets", params = {"validated"})
    List<SingleTicket> ValidatedTickets( @RequestParam("validated") Boolean validated){
        return repository.findByValidated( validated);
    }

    @GetMapping(value = "/single_tickets", params = {"user_id", "validated"})
    List<SingleTicket> UserSTickets(@RequestParam("user_id") Integer userId, @RequestParam("validated") Boolean validated){
        return repository.findByUserIdAndValidated(userId, validated);
    }

    @PutMapping("/single_tickets/validate/{id}")
    Optional<SingleTicket> ValidateTicket(@PathVariable Integer id){
        return repository.findById(id)
                .map(singleTicket -> {
                    singleTicket.setValidated(Boolean.TRUE);
                    return repository.save(singleTicket);
                });
    }




}
