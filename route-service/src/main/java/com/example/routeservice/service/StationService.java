package com.example.routeservice.service;

import com.example.routeservice.model.Station;
import com.example.routeservice.repository.StationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationService {

    private final StationRepository stationRepository;

    public StationService(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    public List<Station> findAll() {
        return stationRepository.findAll();
    }

    public Station findById(Integer id) {
        return stationRepository.findById(id).orElseThrow();
    }

    public Station createStation(String stationName) {
        Station newStation = new Station(stationName);
        return stationRepository.save(newStation);
    }

    public Station updateStation(Integer id, String stationName) {
        Station station = stationRepository.findById(id).orElseThrow();
        station.setStationName(stationName);
        return stationRepository.save(station);
    }

    public void deleteById(Integer id) {
        Station station = stationRepository.findById(id).orElseThrow();
        stationRepository.deleteById(id);
    }

}
