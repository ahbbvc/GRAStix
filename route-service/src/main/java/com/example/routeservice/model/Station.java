package com.example.routeservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String stationName;

    @JsonIgnore
    @OneToMany(mappedBy = "station", cascade = CascadeType.PERSIST)
    private List<RouteStation> routeStations;

    public Station() {
    }

    public Station(String stationName) {
        this.stationName = stationName;
    }

    public Integer getId() {
        return id;
    }

    public String getStationName() {
        return stationName;
    }

    public List<RouteStation> getRouteStations() {
        return routeStations;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public void setRouteStations(List<RouteStation> routeStations) {
        this.routeStations = routeStations;
    }

    public void addRouteStation(RouteStation routeStation) {
        this.routeStations.add(routeStation);
    }
}
