package com.example.ticketservice.Entity;

import javax.persistence.*;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Entity
public class MonthlyTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name= "user_id")
    private User user;

    @ManyToMany
    private List<Route> routes;

    @Column (nullable = false)
    private String month;

    @Column(nullable = false)
    private Boolean validated;

    public MonthlyTicket(){

    }
    public MonthlyTicket(String month ){
        this.month= month;
        this.validated= false;
    }
    public List<Route> getRoutes() {
        return routes;
    }


    public Boolean getValidated() {
        return validated;
    }

    public User getUser() {
        return user;
    }

    public Integer getId() {
        return id;
    }

    public String getMonth() {
        return month;
    }
}
