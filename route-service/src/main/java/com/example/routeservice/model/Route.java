package com.example.routeservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String routeName;

    @Column(nullable = false)
    private String transportType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "route", cascade = CascadeType.PERSIST)
    private List<RouteStation> routeStations;

    public Route() {
    }

    public Route(String routeName, String transportType) {
        this.routeName = routeName;
        this.transportType = transportType;
    }

    public Integer getId() {
        return id;
    }

    public String getRouteName() {
        return routeName;
    }

    public String getTransportType() {
        return transportType;
    }

    public User getUser() {
        return user;
    }

    public List<RouteStation> getRouteStations() {
        return routeStations;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setRouteStations(List<RouteStation> routeStations) {
        this.routeStations = routeStations;
    }

    public void addRouteStation(RouteStation routeStation) {
        this.routeStations.add(routeStation);
    }
}
