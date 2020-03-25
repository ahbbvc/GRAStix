package com.example.routeservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
public class TimeTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @JsonFormat(pattern="dd-M-yyyy hh:mm:ss")
    private Date timeOfArrival;

    @Column
    @JsonFormat(pattern="dd-M-yyyy hh:mm:ss")
    private Date timeOfDeparture;

    @ManyToOne
    @JoinColumn(name = "routeStation_id")
    private RouteStation routeStation;

    public TimeTable() {
    }

    public TimeTable(Date timeOfArrival, Date timeOfDeparture) {
        this.timeOfArrival = timeOfArrival;
        this.timeOfDeparture = timeOfDeparture;
    }

    public Integer getId() {
        return id;
    }

    public Date getTimeOfArrival() {
        return timeOfArrival;
    }

    public Date getTimeofDeparture() {
        return timeOfDeparture;
    }

    public RouteStation getRouteStation() {
        return routeStation;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTimeOfArrival(Date timeOfArrival) {
        this.timeOfArrival = timeOfArrival;
    }

    public void setTimeOfDeparture(Date timeOfDeparture) {
        this.timeOfDeparture = timeOfDeparture;
    }

    public void setRouteStation(RouteStation routeStation) {
        this.routeStation = routeStation;
    }
}
