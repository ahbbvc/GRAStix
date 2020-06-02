package com.example.ticketservice.Controller;

import com.example.ticketservice.Model.*;
import com.example.ticketservice.Service.MTicketService;
import com.example.ticketservice.Wrappers.MTicketResponseWrapper;
import com.example.ticketservice.Wrappers.RequestWraper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
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
    public List<MonthlyTicket> AllMTickets() {
        return mTicketService.findAll();
    }

    @PostMapping("/monthly_tickets")
    public MTicketResponseWrapper newMTicket(@RequestBody RequestWraper rp) {
        User u = restTemplate.getForObject("http://user-service/user/" + rp.getUserId(), User.class);
        List<Route> routes = new ArrayList<Route>();
        for (Integer routeId : rp.getRoutes()) {
            Route r = restTemplate.getForObject("http://route-service/routes/" + routeId, Route.class);
            routes.add(r);
        }
        MonthlyTicket mt = mTicketService.newMTicket(rp.getUserId(), rp.getRoutes(), rp.getMonth());
        return new MTicketResponseWrapper(mt.getId(), u, routes, mt.getValidated(), mt.getMonth());
    }

    @GetMapping("/monthly_tickets/{id}")
    public ResponseEntity<MonthlyTicket> MTicketById(@PathVariable Integer id) throws Exception {
        return mTicketService.findById(id);
    }

    @GetMapping(value = "/monthly_tickets", params = "user_id")
    public List<MTicketResponseWrapper> UserMTickets(@RequestParam("user_id") Integer userId) {
        List<MonthlyTicket> mtickets = mTicketService.findUserMickets(userId);
        List<MTicketResponseWrapper> mts = new ArrayList<MTicketResponseWrapper>();
        User u = restTemplate.getForObject("http://user-service/user/" + userId, User.class);
        for (MonthlyTicket mt : mtickets) {
            List<Route> routes = new ArrayList<Route>();
            for (MTicketRoute mtr : mt.getRoutes()) {
                Route r = restTemplate.getForObject("http://route-service/routes/" + mtr.getRoute_id(), Route.class);
                routes.add(r);
            }
            mts.add(new MTicketResponseWrapper(mt.getId(), u, routes, mt.getValidated(), mt.getMonth()));
        }
        return mts;
    }

    @GetMapping(value = "/monthly_tickets", params = {"user_id", "validated"})
    public List<MonthlyTicket> ValidatedMTickets(@RequestParam("user_id") Integer userId, @RequestParam("validated") Boolean validated) {
        return mTicketService.findValidatedUserMTickets(userId, validated);
    }

    @GetMapping(value = "/monthly_tickets", params = {"validated"})
    public List<MonthlyTicket> ValidatedMTickets(@RequestParam("validated") Boolean validated) {
        return mTicketService.findValidatedMTickets(validated);
    }

    @PutMapping("/monthly_tickets/validate/{id}")
    public Optional<MonthlyTicket> ValidateTicket(@PathVariable Integer id) throws Exception {
        return mTicketService.validateMTicket(id);
    }

}
