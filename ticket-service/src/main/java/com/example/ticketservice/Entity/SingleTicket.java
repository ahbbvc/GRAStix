package com.example.ticketservice.Entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class SingleTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name= "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="route_id")
    private Route route;

    @Column(nullable = false)
    private Timestamp time;

    @Column (nullable = false)
    private Boolean validated;

    public SingleTicket(){
        this.time = new Timestamp(System.currentTimeMillis());
        this.validated= false;
    }
    public SingleTicket(Timestamp time, Boolean validated){
        this.time= time;
        this.validated= validated;
    }

    public Integer getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Timestamp getTime() {
        return time;
    }

    public Boolean getValidated() {
        return validated;
    }
}
