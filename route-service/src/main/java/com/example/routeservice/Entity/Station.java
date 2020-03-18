package com.example.routeservice.Entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String stationName;

    private String location;

    @OneToMany(mappedBy = "station")
    private List<RouteStation> routeStations;

    public Station() {
    }

    public Station(String stationName, String location) {
        this.stationName = stationName;
        this.location = location;
    }

    public Integer getId() {
        return id;
    }

    public String getStationName() {
        return stationName;
    }

    public String getLocation() {
        return location;
    }

    public List<RouteStation> getRouteStations() {
        return routeStations;
    }

}
