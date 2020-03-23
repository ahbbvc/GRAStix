package com.example.routeservice.Controller;

import com.example.routeservice.Entity.Station;
import com.example.routeservice.Repository.StationRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/stations")
public class StationController {

    private final StationRepository repository;

    StationController(StationRepository repository) {
        this.repository = repository;
    }

    @GetMapping("")
    List<Station> findAllStations() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    Optional<Station> findStation(@PathVariable Integer id) {
        return repository.findById(id);
    }

    @PostMapping("")
    Station createStation(@RequestBody Station data) {
        return repository.save(data);
    }

    @DeleteMapping("/{id}")
    void deleteStation(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}
