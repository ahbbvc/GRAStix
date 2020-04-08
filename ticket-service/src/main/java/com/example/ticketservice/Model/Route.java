package com.example.ticketservice.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;


public class Route {
    private Integer id;

    private String routeName;

    private String transportType;
    User user;

    public Route() {
    }

    public Route(Integer id, String routeName, String transportType, User user ) {
        this.id = id;
        this.routeName = routeName;
        this.transportType = transportType;
        this.user = user;

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



    public void setId(Integer id) {
        this.id = id;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }


}
