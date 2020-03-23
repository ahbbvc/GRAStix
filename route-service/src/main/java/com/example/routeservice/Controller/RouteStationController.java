package com.example.routeservice.Controller;

import com.example.routeservice.Entity.Route;
import com.example.routeservice.Entity.RouteStation;
import com.example.routeservice.Entity.Station;
import com.example.routeservice.Entity.TimeTable;
import com.example.routeservice.Repository.RouteRepository;
import com.example.routeservice.Repository.RouteStationRepository;
import com.example.routeservice.Repository.StationRepository;
import com.example.routeservice.Repository.TimeTableRepository;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.sql.Time;
import java.util.List;

@RestController
@RequestMapping("/routestations")
public class RouteStationController {

    private final RouteStationRepository repository;
    private final RouteRepository routeRepository;
    private final StationRepository stationRepository;
    private  final TimeTableRepository timeTableRepository;

    RouteStationController(RouteStationRepository repository, RouteRepository routeRepository,
                           StationRepository stationRepository, TimeTableRepository timeTableRepository) {
        this.repository = repository;
        this.routeRepository = routeRepository;
        this.stationRepository = stationRepository;
        this.timeTableRepository = timeTableRepository;
    }

    @GetMapping("")
    List<RouteStation> findAll() {return repository.findAll(); }

    @PostMapping("")
    RouteStation createRouteStation(@RequestParam("route_id") Integer route_id, @RequestParam("station_id") Integer station_id) throws Exception {
        Route route = routeRepository.findById(route_id)
                .orElseThrow(() -> new Exception(""));
        Station station = stationRepository.findById(station_id)
                .orElseThrow(() -> new Exception(""));

        RouteStation newRouteStation = new RouteStation();

        newRouteStation.setRoute(route);
        newRouteStation.setStation(station);

        station.addRouteStation(newRouteStation);
        route.addRouteStation(newRouteStation);

        return repository.save(newRouteStation);
    }

    @PostMapping("/{id}/addtimetable")
    RouteStation addTimeTable(@PathVariable Integer id, @RequestBody TimeTable timeTable) throws Exception {
        RouteStation routeStation = repository.findById(id)
                .orElseThrow(() -> new Exception(""));

        timeTable.setRouteStation(routeStation);
        routeStation.addTimeTable(timeTable);

        timeTableRepository.save(timeTable);

        return routeStation;
    }

    @DeleteMapping("/{id}")
    void deleteRouteStation(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}
