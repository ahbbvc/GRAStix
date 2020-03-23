package com.example.routeservice.Repository;

import com.example.routeservice.Entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationRepository extends JpaRepository<Station, Integer> {
}