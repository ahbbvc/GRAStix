package com.example.routeservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @NotNull(message = "Station name cannot be null")
    @NotBlank(message = "Station name cannot be empty")
    private String stationName;

    @JsonIgnore
    @OneToMany(mappedBy = "station", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RouteStation> routeStations;

    public Station() {
    }

    public Station(String stationName) {
        this.stationName = stationName;
    }

    public Station(Integer id, String stationName) {
        this.id = id;
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

}
