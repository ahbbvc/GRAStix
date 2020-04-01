package com.example.ticketservice.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
public class MTicketRoute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @JsonBackReference
    @ManyToOne
    @JoinColumn
    private MonthlyTicket mticket;
    private Integer route_id;
    public MTicketRoute(){
    }
    public MTicketRoute(Integer route_id, MonthlyTicket mticket) {
        this.route_id= route_id;
        this.mticket=mticket;

    }

    public MonthlyTicket getMticket() {
        return mticket;
    }

    public Integer getRoute_id() {
        return route_id;
    }

    public void setMticket(MonthlyTicket mticket) {
        this.mticket = mticket;
    }
}
