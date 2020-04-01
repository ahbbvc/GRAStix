package com.example.routeservice.controller;

import com.example.routeservice.model.Route;
import com.example.routeservice.service.RouteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/routes")
public class RouteController {

    private final RouteService routeService;

    RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping("")
    List<Route> getAllRoutes() {
        return routeService.findAll();
    }

    @GetMapping("/{id}")
    Route getRouteById(@PathVariable Integer id) {
        return routeService.findById(id);
    }

    @GetMapping("/search")
    List<Route> getRoutesByNameAndType(@RequestParam("name") String routeName, @RequestParam("type") String transportType) {
        return routeService.findByRouteNameAndTransportType(routeName, transportType);
    }

    @PostMapping("")
    Route createRoute(@RequestBody Route data) {
        return routeService.createRoute(data.getRouteName(), data.getTransportType());
    }

    @DeleteMapping("/{id}")
    void deleteRoute(@PathVariable Integer id) {
        routeService.deleteById(id);
    }

}
