package com.example.routeservice.Entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class RouteStation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;

    @ManyToOne
    @JoinColumn(name = "station_id")
    private Station station;

    @OneToMany(mappedBy = "routeStation")
    private List<TimeTable> timeTables;

    public RouteStation() {
    }

    public Integer getId() {
        return id;
    }

    public Route getRoute() {
        return route;
    }

    public Station getStation() {
        return station;
    }

    public List<TimeTable> getTimeTables() {
        return timeTables;
    }
}
