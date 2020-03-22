package com.example.ticketservice.Entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String routeName;

    @Column(nullable = false)
    private String transportType;

    @OneToMany(mappedBy = "route")
    private List<SingleTicket> tickets;

   /* @ManyToMany
    private List<MonthlyTicket> mtickets;*/

    public Route(){

    }
    public Integer getId() {
        return id;
    }

    public String getRouteName() {
        return routeName;
    }

    public String getTransportType() {
        return transportType;
    }


    public List<SingleTicket> getTickets() {
        return tickets;
    }

   /* public List<MonthlyTicket> getMtickets() {
        return mtickets;
    }*/
}
