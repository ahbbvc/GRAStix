package com.example.routeservice.service;

import com.example.routeservice.model.RouteStation;
import com.example.routeservice.model.TimeTable;
import com.example.routeservice.repository.TimeTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TimeTableService {

    private final TimeTableRepository timeTableRepository;
    @Autowired
    private RouteStationService routeStationService;

    public TimeTableService(TimeTableRepository timeTableRepository) {
        this.timeTableRepository = timeTableRepository;
    }

    public List<TimeTable> findAll() { return timeTableRepository.findAll(); }

    public TimeTable findById(Integer id) {
        return timeTableRepository.findById(id).orElseThrow(); }

    public TimeTable createTimeTable(Date timeOfArrival, Date timeOfDeparture) {
        TimeTable newTimeTable = new TimeTable(timeOfArrival, timeOfDeparture);
        return timeTableRepository.save(newTimeTable);
    }

    public TimeTable addTimeTable(Integer timeTableId, Integer routeStationId) {
        TimeTable timeTable = findById(timeTableId);
        RouteStation routeStation = routeStationService.findById(routeStationId);

        timeTable.setRouteStation(routeStation);

        return timeTableRepository.save(timeTable);
    }

    public void deleteById(Integer id) { timeTableRepository.deleteById(id);}
}
