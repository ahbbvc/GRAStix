package com.example.routeservice.Repository;

import com.example.routeservice.Entity.RouteStation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteStationRepository extends JpaRepository<RouteStation, Integer> {
}