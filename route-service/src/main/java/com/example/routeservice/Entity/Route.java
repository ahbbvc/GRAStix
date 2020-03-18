package com.example.routeservice.Entity;

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

    @OneToMany(mappedBy = "route")
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
}
