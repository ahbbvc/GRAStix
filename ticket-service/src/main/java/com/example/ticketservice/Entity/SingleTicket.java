package com.example.ticketservice.Entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
public class SingleTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

   /* @ManyToOne
    @JoinColumn(name= "user_id")*/
   @NotNull(message = "User cannot be null")
    private Integer userId;

    /*@ManyToOne
    @JoinColumn(name="route_id")*/
    @NotNull(message = "Route cannot be null")
    private Integer routeId;


    @Column(nullable = false)
    private Timestamp time;

    @Column (nullable = false)
    private Boolean validated;

    public SingleTicket(){
        this.time = new Timestamp(System.currentTimeMillis());
        this.validated= false;
    }
    public SingleTicket(Integer userId, Integer routeId ){
        this.userId= userId;
        this.routeId=routeId;
        this.time = new Timestamp(System.currentTimeMillis());
        this.validated= false;
    }

    public Integer getId() {
        return id;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setValidated(Boolean validated) {
        this.validated = validated;
    }
    public Boolean getValidated() {
        return validated;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getRouteId() {
        return routeId;
    }
}
