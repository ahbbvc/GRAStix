package com.example.ticketservice.Controller;

import com.example.ticketservice.Model.*;
import com.example.ticketservice.Repository.STicketRepository;
import com.example.ticketservice.Service.STicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
public class STicketController {
    private final STicketService sTicketService;
    final RestTemplate restTemplate;

    STicketController(STicketService sTicketService, RestTemplate restTemplate) {
        this.sTicketService= sTicketService;
        this.restTemplate = restTemplate;
    }
    @GetMapping("/single_tickets")
    public List<SingleTicket> AllSTickets(){
        return sTicketService.findAll();
    }

    @PostMapping("/single_tickets")
    public STicketResponseWraper newSTicket(@Validated @RequestBody SingleTicket st){
        SingleTicket st2 =sTicketService.newSTicket(st);
        User u  = restTemplate.getForObject("http://user-service/user/" + st2.getUserId() , User.class);
        Route r = restTemplate.getForObject("http://route-service/routes/" + st2.getRouteId(), Route.class);
        return new STicketResponseWraper(st2.getId(), u, r, st2.getValidated(), st2.getTime());
    }
    @GetMapping("/single_tickets/{id}")
    public SingleTicket STicketById(@PathVariable Integer id){

        return sTicketService.findById(id);
        //return restTemplate.getForObject("http://user-service/user/9" , User.class);

    }
    @DeleteMapping("/single_tickets/{id}")
    ResponseEntity<Object> DeleteSTicket(@PathVariable Integer id){
        return sTicketService.deleteSTicket(id);
    }

    @GetMapping(value = "/single_tickets", params = "user_id")
    public List<STicketResponseWraper> UserSTickets(@RequestParam("user_id") Integer userId){
        List<SingleTicket> stickets =  sTicketService.findUserSTickets(userId);
        List<STicketResponseWraper> sts =  new ArrayList<STicketResponseWraper>();
        for (SingleTicket st : stickets){
            User u  = restTemplate.getForObject("http://user-service/user/" + st.getUserId() , User.class);
            Route r = restTemplate.getForObject("http://route-service/routes/" + st.getRouteId(), Route.class);
            sts.add(new STicketResponseWraper(st.getId(), u, r, st.getValidated(), st.getTime()));
        }
        return  sts;
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
