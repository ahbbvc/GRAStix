package com.example.routeservice.controller;

import com.example.routeservice.model.Route;
import com.example.routeservice.model.User;
import com.example.routeservice.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/routes")
public class RouteController {

    @Autowired
    private RestTemplate restTemplate;

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
        // Hardkodiran userid
        Integer userId = 9;
        User user = restTemplate.getForObject("http://user-service/user/" + userId, User.class);
        return routeService.createRoute(data.getRouteName(), data.getTransportType(), user);
    }

    @PutMapping("/{id}")
    Route updateRoute(@PathVariable Integer id, @RequestBody Route data) {
        return routeService.updateRoute(id, data.getRouteName(), data.getTransportType());
    }

    @DeleteMapping("/{id}")
    void deleteRoute(@PathVariable Integer id) {
        routeService.deleteById(id);
    }

}
