package com.example.routeservice.repository;

import com.example.routeservice.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RouteRepository extends JpaRepository<Route, Integer> {

    List<Route> findByRouteNameAndTransportType(String routeName, String transportType);

}