package com.example.routeservice.controller;

import com.example.routeservice.model.Route;
import com.example.routeservice.rabbitmq.Sender;
import com.example.routeservice.service.RouteService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/routes")
public class RouteController {

    private final RouteService routeService;
    private final Sender sender;

    RouteController(RouteService routeService, Sender sender) {
        this.routeService = routeService;
        this.sender = sender;
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
    Route createRoute(@Valid @RequestBody Route data) {
        return routeService.createRoute(data.getRouteName(), data.getTransportType(), data.getUser());
    }

    @PutMapping("/{id}")
    Route updateRoute(@PathVariable Integer id, @Valid @RequestBody Route data) {
        return routeService.updateRoute(id, data.getRouteName(), data.getTransportType());
    }

    @DeleteMapping("/{id}")
    void deleteRoute(@PathVariable Integer id) {
        //routeService.deleteById(id);
        sender.send(id);
    }

}
