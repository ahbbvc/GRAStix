package com.example.routeservice.Repository;

import com.example.routeservice.Entity.Route;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RouteRepository extends CrudRepository<Route, Integer> {
}