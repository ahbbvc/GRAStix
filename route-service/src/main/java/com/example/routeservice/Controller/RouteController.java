package com.example.routeservice.Controller;

import com.example.routeservice.Entity.Route;
import com.example.routeservice.Repository.RouteRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/routes")
public class RouteController {

    private final RouteRepository repository;

    RouteController(RouteRepository repository) {
        this.repository = repository;
    }

    @GetMapping("")
    List<Route> findAllRoutes() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    Optional<Route> findRouteById(@PathVariable Integer id) {
        return repository.findById(id);
    }

    @GetMapping("/search")
    List<Route> findRoutesByNameAndType(@RequestParam("name") String routeName, @RequestParam("type") String transportType) {
        return repository.findByRouteNameAndTransportType(routeName, transportType);
    }

    @PostMapping("")
    Route createRoute(@RequestBody Route data) {
        return repository.save(data);
    }

    @DeleteMapping("/{id}")
    void deleteRoute(@PathVariable Integer id) {
        repository.deleteById(id);
    }

}
