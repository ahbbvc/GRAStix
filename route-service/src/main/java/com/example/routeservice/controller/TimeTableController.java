package com.example.routeservice.controller;

import com.example.routeservice.model.TimeTable;
import com.example.routeservice.service.TimeTableService;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
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
    TimeTable createTimeTable(@RequestBody TimeTable data) {
        return timeTableService.createTimeTable(data.getTimeOfArrival(), data.getTimeofDeparture());
    }

    @PostMapping("/addtimetable")
    TimeTable addTimeTable(@RequestParam("timetable_id") Integer timeTableId, @RequestParam("routestation_id") Integer routeStationId) {
        return timeTableService.addTimeTable(timeTableId, routeStationId);
    }

    @DeleteMapping("/{id}")
    void deleteTimeTable(@PathVariable Integer id) {
        timeTableService.deleteById(id);
    }
}
