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
    RouteStation getRouteStationById(@PathVariable Integer id) {
        return routeStationService.findById(id);
    }

    @PostMapping("")
    RouteStation createRouteStation(@RequestParam("route") Integer routeId,
                                    @RequestParam("station") Integer stationId) {
        return routeStationService.createRouteStation(routeId, stationId);
    }

    @DeleteMapping("/{id}")
    void deleteRouteStation(@PathVariable Integer id) {
        routeStationService.deleteById(id);
    }

}
