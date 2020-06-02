package com.example.routeservice.controller;

import com.example.routeservice.model.Station;
import com.example.routeservice.service.StationService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/stations")
public class StationController {

    private final StationService stationService;

    StationController(StationService stationService) {
        this.stationService = stationService;
    }

    @GetMapping("")
    List<Station> getAllStations() {
        return stationService.findAll();
    }

    @GetMapping("/{id}")
    Station getStationById(@PathVariable Integer id) {
        return stationService.findById(id);
    }

    @PostMapping("")
    Station createStation(@Valid @RequestBody Station data) {
        return stationService.createStation(data.getStationName());
    }

    @PutMapping("/{id}")
    Station updateStation(@PathVariable Integer id, @Valid @RequestBody Station data) {
        return stationService.updateStation(id, data.getStationName());
    }

    @DeleteMapping("/{id}")
    void deleteStation(@PathVariable Integer id) {
        stationService.deleteById(id);
    }

}
