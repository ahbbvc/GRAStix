package com.example.routeservice.Entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class TimeTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Date timeOfArrival;

    @Column
    private Date timeofDeparture;

    @ManyToOne
    @JoinColumn(name = "routeStation_id")
    private RouteStation routeStation;

    public TimeTable() {
    }

    public Integer getId() {
        return id;
    }

    public Date getTimeOfArrival() {
        return timeOfArrival;
    }

    public Date getTimeofDeparture() {
        return timeofDeparture;
    }

    public RouteStation getRouteStation() {
        return routeStation;
    }

}
