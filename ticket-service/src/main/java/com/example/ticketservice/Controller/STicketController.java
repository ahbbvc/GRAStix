package com.example.ticketservice.Controller;

import com.example.ticketservice.Model.SingleTicket;
import com.example.ticketservice.Repository.STicketRepository;
import com.example.ticketservice.Service.STicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
public class STicketController {
    private final STicketService sTicketService;
    STicketController(STicketService sTicketService) {
        this.sTicketService= sTicketService;
    }
    @GetMapping("/single_tickets")
    public List<SingleTicket> AllSTickets(){
        return sTicketService.findAll();
    }

    @PostMapping("/single_tickets")
    public SingleTicket newSTicket(@Validated @RequestBody SingleTicket st){
        return sTicketService.newSTicket(st);
    }

    @GetMapping("/single_tickets/{id}")
    public SingleTicket STicketById(@PathVariable Integer id){
        return sTicketService.findById(id);
    }
    @DeleteMapping("/single_tickets/{id}")
    ResponseEntity<Object> DeleteSTicket(@PathVariable Integer id){
        return sTicketService.deleteSTicket(id);
    }

    @GetMapping(value = "/single_tickets", params = "user_id")
    public List<SingleTicket> UserSTickets(@RequestParam("user_id") Integer userId){
        return sTicketService.findUserSTickets(userId);
    }

    @GetMapping(value = "/single_tickets", params = "route_id")
    public List<SingleTicket> SticketsByRoute(@RequestParam("route_id") Integer routeId){
        return sTicketService.findSticketsByRoute(routeId);
    }

    @GetMapping(value = "/single_tickets", params = {"validated"})
    public List<SingleTicket> ValidatedTickets( @RequestParam("validated") Boolean validated){
        return sTicketService.findValidatedTickets(validated);
    }

    @GetMapping(value = "/single_tickets", params = {"user_id", "validated"})
    public List<SingleTicket> validatedUserSTickets(@RequestParam("user_id") Integer userId, @RequestParam("validated") Boolean validated){
        return sTicketService.findValidatedUserSTickets(userId, validated);
    }

    @PutMapping("/single_tickets/validate/{id}")
    public Optional<SingleTicket> ValidateTicket(@PathVariable Integer id){
        return sTicketService.validateSTicket(id);
    }




}
