package com.example.routeservice.service;

import com.example.routeservice.model.Route;
import com.example.routeservice.model.RouteStation;
import com.example.routeservice.model.Station;
import com.example.routeservice.repository.RouteStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteStationService {

    private final RouteStationRepository routeStationRepository;
    @Autowired
    private RouteService routeService;
    @Autowired
    private StationService stationService;

    public RouteStationService(RouteStationRepository routeStationRepository) {
        this.routeStationRepository = routeStationRepository;
    }

    public List<RouteStation> findAll() { return routeStationRepository.findAll(); }

    public RouteStation findById(Integer id) {
        return routeStationRepository.findById(id).orElseThrow();
    }

    public RouteStation createRouteStation(Integer routeId, Integer stationId) {
        Route route = routeService.findById(routeId);
        Station station = stationService.findById(stationId);

        RouteStation newRouteStation = new RouteStation();

        newRouteStation.setRoute(route);
        newRouteStation.setStation(station);

        return routeStationRepository.save(newRouteStation);
    }

    public void deleteById(Integer id) {
        routeStationRepository.deleteById(id);
    }
}
