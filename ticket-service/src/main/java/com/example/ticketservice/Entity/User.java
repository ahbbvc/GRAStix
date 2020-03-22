package com.example.ticketservice.Entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "user")
    private List<SingleTicket> tickets;

    @OneToMany(mappedBy = "user")
    private List<MonthlyTicket> mtickets;
    public User(){
    }
    public Integer getId() {
        return id;
    }
    public List<SingleTicket> getTickets() {
        return tickets;
    }

    public List<MonthlyTicket> getMtickets() {
        return mtickets;
    }
}