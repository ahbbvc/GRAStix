package com.example.routeservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
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

    @JsonIgnore
    @OneToMany(mappedBy = "routeStation", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TimeTable> timeTables = new ArrayList<>();

    public RouteStation() {
    }

    public RouteStation(Route route, Station station) {
        this.route = route;
        this.station = station;
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

    public void setId(Integer id) {
        this.id = id;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public void setTimeTables(List<TimeTable> timeTables) {
        this.timeTables = timeTables;
    }

}
