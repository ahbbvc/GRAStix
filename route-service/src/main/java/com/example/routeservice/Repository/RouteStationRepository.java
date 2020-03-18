package com.example.routeservice.Repository;

import com.example.routeservice.Entity.RouteStation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RouteStationRepository extends CrudRepository<RouteStation, Integer> {
}