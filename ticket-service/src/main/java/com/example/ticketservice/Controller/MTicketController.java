package com.example.ticketservice.Controller;

import com.example.ticketservice.Model.MonthlyTicket;
import com.example.ticketservice.Model.RequestWraper;
import com.example.ticketservice.Service.MTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RestController
public class MTicketController {
    private final MTicketService mTicketService;
    @Autowired
    RestTemplate restTemplate;

    public MTicketController(MTicketService mTicketService) {
        this.mTicketService = mTicketService;
    }

    @GetMapping("/monthly_tickets")
    public List<MonthlyTicket> AllMTickets(){
        return mTicketService.findAll();
    }

    @PostMapping("/monthly_tickets")
    public MonthlyTicket newMTicket(@RequestBody RequestWraper rp){
        return mTicketService.newMTicket(rp.getUserId(), rp.getRoutes(), rp.getMonth());
    }

    @GetMapping("/monthly_tickets/{id}")
    public MonthlyTicket MTicketById(@PathVariable Integer id){
        return mTicketService.findById(id);
    }

    @GetMapping(value = "/monthly_tickets", params = "user_id")
    public List<MonthlyTicket> UserMTickets(@RequestParam("user_id") Integer userId){
        return mTicketService.findUserMickets(userId);
    }
    @GetMapping(value = "/monthly_tickets", params = { "user_id" ,"validated"})
    public List<MonthlyTicket> ValidatedMTickets(@RequestParam("user_id") Integer userId, @RequestParam("validated") Boolean validated){
        return mTicketService.findValidatedUserMTickets(userId,validated);
    }

    @PutMapping("/monthly_tickets/validate/{id}")
    public Optional<MonthlyTicket> ValidateTicket(@PathVariable Integer id){
       return mTicketService.validateMTicket(id);
    }


}
