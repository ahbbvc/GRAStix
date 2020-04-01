package com.example.routeservice.repository;

import com.example.routeservice.model.RouteStation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteStationRepository extends JpaRepository<RouteStation, Integer> {
}