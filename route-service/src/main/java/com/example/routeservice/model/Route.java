package com.example.routeservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @NotNull(message = "Route name cannot be null")
    @NotBlank(message = "Route name cannot be empty")
    private String routeName;

    @Column
    @NotNull(message = "Transport type cannot be null")
    @NotBlank(message = "Transport type cannot be empty")
    private String transportType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "route", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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

}
