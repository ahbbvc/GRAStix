package com.example.ticketservice.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class MonthlyTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "User can not be null")
    private Integer userId;
    @JsonManagedReference
    @OneToMany(mappedBy = "mticket", cascade = CascadeType.PERSIST)
    private List<MTicketRoute> routes;
    @NotNull(message = "Month can not be null")
    @Column(nullable = false)
    private String month;

    @Column(nullable = false)
    private Boolean validated;

    public MonthlyTicket() {

    }

    public MonthlyTicket(Integer userId, String month) {
        this.month = month;
        this.validated = false;
        this.userId = userId;

    }

    public MonthlyTicket(Integer userId, String month, List<MTicketRoute> routes) {
        this.month = month;
        this.validated = false;
        this.userId = userId;
        this.routes = routes;

    }

    public Boolean getValidated() {
        return validated;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public void setValidated(Boolean validated) {
        this.validated = validated;
    }

    public Integer getId() {
        return id;
    }

    public String getMonth() {
        return month;
    }

    public Integer getUserId() {
        return userId;
    }

    public List<MTicketRoute> getRoutes() {
        return routes;
    }

    public void setRoutes(List<MTicketRoute> routes) {
        this.routes = routes;
    }

}
