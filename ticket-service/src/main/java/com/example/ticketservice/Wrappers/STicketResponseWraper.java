package com.example.ticketservice.Wrappers;

import com.example.ticketservice.Model.Route;
import com.example.ticketservice.Model.User;

import java.sql.Timestamp;

public class STicketResponseWraper {
    private Integer id;
    private User user;
    private Route route;
    private Boolean validated;
    private Timestamp time;


    public STicketResponseWraper(Integer id, User user, Route route, Boolean validated, Timestamp time) {
        this.id = id;
        this.user = user;
        this.route = route;
        this.validated = validated;
        this.time = time;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public Route getRoute() {
        return route;
    }

    public Boolean getValidated() {
        return validated;
    }

    public Timestamp getTime() {
        return time;
    }
}
