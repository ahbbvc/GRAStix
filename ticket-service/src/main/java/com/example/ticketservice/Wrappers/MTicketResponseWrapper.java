package com.example.ticketservice.Wrappers;

import com.example.ticketservice.Model.Route;
import com.example.ticketservice.Model.User;

import java.util.List;

public class MTicketResponseWrapper {
    private Integer id;
    private User user;
    private List<Route> routes;
    private Boolean validated;
    private String month;

    public MTicketResponseWrapper(Integer id,User user, List<Route> routes, Boolean validated, String month) {
        this.id = id;
        this.user = user;
        this.routes = routes;
        this.validated = validated;
        this.month = month;
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

    public Boolean getValidated() {
        return validated;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public String getMonth() {
        return month;
    }
}
