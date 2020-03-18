package com.example.routeservice.Entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "user")
    private List<Route> routes;

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public List<Route> getRoutes() {
        return routes;
    }

}