package com.example.ticketservice.Controller;

import com.example.ticketservice.Entity.MonthlyTicket;
import com.example.ticketservice.Entity.SingleTicket;
import com.example.ticketservice.Repository.MTicketRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class MTicketController {
    private final MTicketRepository repository;
    MTicketController(MTicketRepository repository){
        this.repository= repository;
    }

    @GetMapping("/monthly_tickets")
    List<MonthlyTicket> AllMTickets(){
        return repository.findAll();
    }

    @PostMapping("/monthly_tickets")
    MonthlyTicket newMTicket(@RequestBody MonthlyTicket mt){
        return repository.save(mt);
    }

    @GetMapping("/monthly_tickets/{id}")
    Optional<MonthlyTicket> MTicketsById(@PathVariable Integer id){
        return repository.findById(id);
    }

    @GetMapping(value = "/monthly_tickets", params = "user_id")
    List<MonthlyTicket> UserMTickets(@RequestParam("user_id") Integer userId){
        return repository.findByUserId(userId);
    }
    @GetMapping(value = "/monthly_tickets", params = { "user_id" ,"validated"})
    List<MonthlyTicket> ValidatedMTickets(@RequestParam("user_id") Integer userId, @RequestParam("validated") Boolean validated){
        return repository.findByUserIdAndValidated(userId,validated);
    }

    @PutMapping("/monthly_tickets/validate/{id}")
    Optional<MonthlyTicket> ValidateTicket(@PathVariable Integer id){
        return repository.findById(id)
                .map(monthlyTicket  -> {
                    monthlyTicket.setValidated(Boolean.TRUE);
                    return repository.save(monthlyTicket);
                });
    }


}
