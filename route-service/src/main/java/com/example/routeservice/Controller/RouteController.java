package com.example.routeservice.Controller;

import com.example.routeservice.Entity.Route;
import com.example.routeservice.Repository.RouteRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class RouteController {

    private final RouteRepository repository;

    RouteController(RouteRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/routes")
    List<Route> findAllRoutes() {
        return repository.findAll();
    }

    @GetMapping("/routes{name}")
    List<Route> findRoutesByName(@RequestParam("name") String routeName) {
        return repository.findByRouteName(routeName);
    }

    @GetMapping("/routes{type}")
    List<Route> findRoutesByType(@RequestParam("type") String transportType) {
        return repository.findByTransportType(transportType);
    }

    @GetMapping("/routes{name}{type}")
    List<Route> findRoutesByNameAndType(@RequestParam("name") String routeName, @RequestParam("type") String transportType) {
        return repository.findByRouteNameAndTransportType(routeName, transportType);
    }

    @PostMapping("/routes")
    Route newRoute(@RequestBody Route data) {
        return repository.save(data);
    }

    @DeleteMapping("/routes/{id}")
    void deleteRoute(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}
