package com.example.routeservice.Controller;

import com.example.routeservice.Entity.TimeTable;
import com.example.routeservice.Repository.TimeTableRepository;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.util.List;

@RestController
@RequestMapping("/timetables")
public class TimeTableController {
    private final TimeTableRepository repository;
    private EntityManager session;
    TimeTableController(TimeTableRepository repository) {
        this.repository = repository;
    }

    @GetMapping("")
    List<TimeTable> findAllTimeTables() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    TimeTable findByRouteStation(@PathVariable Integer routeStationId) {
        return findByRouteStation(routeStationId);
    }

    @PostMapping("")
    TimeTable createTimeTable(@RequestBody TimeTable data) {
        return repository.save(data);
    }

    @DeleteMapping("/{id}")
    void deleteTimeTable(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}
