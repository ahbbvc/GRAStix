package com.example.ticketservice.Controller;


import com.example.ticketservice.Model.*;
import com.example.ticketservice.Service.STicketService;
import com.example.ticketservice.Wrappers.STicketResponseWraper;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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
    public STicketResponseWraper newSTicket(@Validated @RequestBody SingleTicket st,  @RequestHeader("authorization")String token) throws Exception{

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<String>("parameters",headers);

        ResponseEntity<User> u  = restTemplate.exchange("http://user-service/user/" + st.getUserId(), HttpMethod.GET,  entity ,User.class);
        ResponseEntity<Route> r = restTemplate.exchange("http://route-service/routes/" + st.getRouteId(),HttpMethod.GET,entity, Route.class);

        SingleTicket st2 = sTicketService.newSTicket(st);
        return new STicketResponseWraper(st2.getId(), u.getBody(), r.getBody(), st2.getValidated(), st2.getTime());
    }
    @GetMapping("/single_tickets/{id}")
    public ResponseEntity<SingleTicket> STicketById(@PathVariable Integer id) throws  Exception{
        return sTicketService.findById(id);
    }
    @DeleteMapping("/single_tickets/{id}")
    ResponseEntity<Object> DeleteSTicket(@PathVariable Integer id) throws Exception{
        return sTicketService.deleteSTicket(id);
    }

    @GetMapping(value = "/single_tickets", params = "user_id")
    public List<STicketResponseWraper> UserSTickets(@RequestParam("user_id") Integer userId, @RequestHeader("authorization")String token){
        List<SingleTicket> stickets =  sTicketService.findUserSTickets(userId);
        List<STicketResponseWraper> sts =  new ArrayList<STicketResponseWraper>();
        HttpHeaders headers = new HttpHeaders();

        headers.add("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<String>("parameters",headers);
        ResponseEntity<User> u  = restTemplate.exchange("http://user-service/user/" + userId, HttpMethod.GET,  entity ,User.class);
        for (SingleTicket st : stickets){
            ResponseEntity<Route> r = restTemplate.exchange("http://route-service/routes/" + st.getRouteId(),HttpMethod.GET,entity, Route.class);
            sts.add(new STicketResponseWraper(st.getId(), u.getBody(), r.getBody(), st.getValidated(), st.getTime()));
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
    public Optional<SingleTicket> ValidateTicket(@PathVariable Integer id) throws Exception{
        return sTicketService.validateSTicket(id);
    }




}
