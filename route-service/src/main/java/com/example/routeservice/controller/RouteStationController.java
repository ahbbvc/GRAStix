package com.example.routeservice.controller;

import com.example.routeservice.model.RouteStation;
import com.example.routeservice.service.RouteStationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/routestations")
public class RouteStationController {

    private final RouteStationService routeStationService;

    public RouteStationController(RouteStationService routeStationService) {
        this.routeStationService = routeStationService;
    }

    @GetMapping("")
    List<RouteStation> getAllRouteStations() {return routeStationService.findAll(); }

    @GetMapping("/{id}")
    RouteStation getRouteStationById(Integer id) {
        return routeStationService.findById(id);
    }

    @PostMapping("")
    RouteStation createRouteStation(@RequestParam("route_id") Integer route_id,
                                    @RequestParam("station_id") Integer station_id) {
        return routeStationService.createRouteStation(route_id, station_id);
    }

    @DeleteMapping("/{id}")
    void deleteRouteStation(@PathVariable Integer id) {
        routeStationService.deleteById(id);
    }
}
