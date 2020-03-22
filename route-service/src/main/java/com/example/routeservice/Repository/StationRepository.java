package com.example.routeservice.Repository;

import com.example.routeservice.Entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StationRepository extends JpaRepository<Station, Integer> {
}