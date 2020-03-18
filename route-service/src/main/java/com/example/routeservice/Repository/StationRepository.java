package com.example.routeservice.Repository;

import com.example.routeservice.Entity.Station;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface StationRepository extends CrudRepository<Station, Integer> {
}