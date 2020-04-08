package com.example.routeservice.service;

import com.example.routeservice.exception.InvalidTimeTableException;
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

    public TimeTable createTimeTable(Date timeOfArrival, Date timeOfDeparture, boolean regular) throws InvalidTimeTableException {
        TimeTable newTimeTable = new TimeTable(timeOfArrival, timeOfDeparture, regular);
        if(timeOfArrival.compareTo(timeOfDeparture) > 0)
            throw new InvalidTimeTableException("Time of arrival must be before time of departure");
        return timeTableRepository.save(newTimeTable);
    }

    public TimeTable addTimeTable(Integer timeTableId, Integer routeStationId) {
        TimeTable timeTable = findById(timeTableId);
        RouteStation routeStation = routeStationService.findById(routeStationId);

        timeTable.setRouteStation(routeStation);

        return timeTableRepository.save(timeTable);
    }

    public TimeTable updateTimeTable(Integer id, Date timeOfArrival, Date timeOfDeparture, boolean regular) {
               TimeTable timeTable = timeTableRepository.findById(id).orElseThrow();
                timeTable.setTimeOfArrival(timeOfArrival);
                timeTable.setTimeOfDeparture(timeOfDeparture);
                timeTable.setRegular(regular);
                return timeTableRepository.save(timeTable);
            }

    public void deleteById(Integer id) {
       TimeTable timeTable = timeTableRepository.findById(id).orElseThrow();
        timeTableRepository.deleteById(id);
   }

}
