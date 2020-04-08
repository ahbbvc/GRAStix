package com.example.routeservice.controller;

import com.example.routeservice.exception.InvalidTimeTableException;
import com.example.routeservice.model.TimeTable;
import com.example.routeservice.service.TimeTableService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/timetables")
public class TimeTableController {

    private final TimeTableService timeTableService;

    public TimeTableController(TimeTableService timeTableService) {
        this.timeTableService = timeTableService;
    }

    @GetMapping("")
    List<TimeTable> getAllTimeTables() {
        return timeTableService.findAll();
    }

    @GetMapping("/{id}")
    TimeTable getTimeTableById(@PathVariable Integer id) {
        return timeTableService.findById(id);
    }

    @PostMapping("")
    TimeTable createTimeTable(@RequestBody TimeTable data) throws InvalidTimeTableException {
        return timeTableService.createTimeTable(data.getTimeOfArrival(), data.getTimeOfDeparture(), data.isRegular());
    }

    @PostMapping("/addtimetable")
    TimeTable addTimeTable(@RequestParam("timetable") Integer timeTableId, @RequestParam("routestation") Integer routeStationId) {
        return timeTableService.addTimeTable(timeTableId, routeStationId);
    }

    @PutMapping("/{id}")
    TimeTable updateTimeTable(@PathVariable Integer id, @RequestBody TimeTable data) {
        return timeTableService.updateTimeTable(id, data.getTimeOfArrival(), data.getTimeOfDeparture(), data.isRegular());
    }

    @DeleteMapping("/{id}")
    void deleteTimeTable(@PathVariable Integer id) {
        timeTableService.deleteById(id);
    }
}
