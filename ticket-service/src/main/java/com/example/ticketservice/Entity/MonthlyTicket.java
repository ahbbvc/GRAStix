package com.example.ticketservice.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    /*@ManyToOne
    @JoinColumn(name= "user_id")*/
    private Integer userId;
    @JsonIgnore
    @OneToMany(mappedBy = "mticket")
    private List<MTicketRoute> routes;

    @Column (nullable = false)
    private String month;

    @Column(nullable = false)
    private Boolean validated;

    public MonthlyTicket(){

    }
    public MonthlyTicket(Integer userId , String month ){
        this.month= month;
        this.validated= false;
        this.userId=userId;

    }

    public Boolean getValidated() {
        return validated;
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
