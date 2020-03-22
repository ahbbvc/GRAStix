package com.example.routeservice.Repository;

import com.example.routeservice.Entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RouteRepository extends JpaRepository<Route, Integer> {

    List<Route> findByRouteNameAndTransportType(String routeName, String transportType);

    List<Route> findByRouteName(String routeName);

    List<Route> findByTransportType(String transportType);
}