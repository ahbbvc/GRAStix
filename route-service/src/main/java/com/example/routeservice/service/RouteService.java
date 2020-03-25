package com.example.routeservice.service;

import com.example.routeservice.model.Route;
import com.example.routeservice.repository.RouteRepository;
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
        //dodati error handling
        return routeRepository.findById(id).orElse(null);
    }

    public List<Route> findByRouteNameAndTransportType(String routeName, String transportType) {
        return routeRepository.findByRouteNameAndTransportType(routeName, transportType);
    }

    public Route createRoute(String routeName, String transportType) {
        Route newRoute = new Route(routeName, transportType);
        return routeRepository.save(newRoute);
    }

    public void deleteById(Integer id) {
        routeRepository.deleteById(id);
    }
}