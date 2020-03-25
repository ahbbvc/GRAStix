package com.example.routeservice.service;

import com.example.routeservice.model.Route;
import com.example.routeservice.model.RouteStation;
import com.example.routeservice.model.Station;
import com.example.routeservice.model.TimeTable;
import com.example.routeservice.repository.RouteStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class RouteStationService {

    private final RouteStationRepository routeStationRepository;
    @Autowired
    private RouteService routeService;
    @Autowired
    private StationService stationService;
    @Autowired
    private TimeTableService timeTableService;

    public RouteStationService(RouteStationRepository routeStationRepository) {
        this.routeStationRepository = routeStationRepository;
    }

    public List<RouteStation> findAll() { return routeStationRepository.findAll(); }

    public RouteStation findById(Integer id) {
        //dodati error handling
        return routeStationRepository.findById(id).orElse(null);
    }

    public RouteStation createRouteStation(Integer routeId, Integer stationId) {
        Route route = routeService.findById(routeId);
        Station station = stationService.findById(stationId);

        RouteStation newRouteStation = new RouteStation();

        newRouteStation.setRoute(route);
        newRouteStation.setStation(station);

        //station.addRouteStation(newRouteStation);
        //route.addRouteStation(newRouteStation);

        return routeStationRepository.save(newRouteStation);
    }

    public void deleteById(Integer id) {
        routeStationRepository.deleteById(id);
    }
}
