package com.example.ticketservice.Exeption;

public class TicketNotFoundException extends Exception {
    public TicketNotFoundException(String ticket, Integer id) {
        super(ticket + " with id= " + id + " was not found");
    }
}
