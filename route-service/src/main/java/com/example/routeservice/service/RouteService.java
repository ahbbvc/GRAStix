package com.example.routeservice.service;

import com.example.routeservice.model.Route;
import com.example.routeservice.model.User;
import com.example.routeservice.repository.RouteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteService {

    private final RouteRepository routeRepository;

    public RouteService(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    public List<Route> findAll() {
        return routeRepository.findAll();
    }

    public Route findById(Integer id) {
        return routeRepository.findById(id).orElseThrow();
    }

    public List<Route> findByRouteNameAndTransportType(String routeName, String transportType) {
        return routeRepository.findByRouteNameAndTransportType(routeName, transportType);
    }

    public Route createRoute(String routeName, String transportType) {
        Route newRoute = new Route(routeName, transportType);
        return routeRepository.save(newRoute);
    }

    public Route updateRoute(Integer id, String routeName, String transportType) {
        Route route = routeRepository.findById(id).orElseThrow();
        route.setId(id);
        route.setRouteName(routeName);
        route.setTransportType(transportType);
        return routeRepository.save(route);
    }

    public void deleteById(Integer id) {
        Route route = routeRepository.findById(id).orElseThrow();
        routeRepository.deleteById(id);
    }
}