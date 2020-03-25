package com.example.routeservice.repository;

import com.example.routeservice.model.TimeTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeTableRepository extends JpaRepository<TimeTable, Integer> {
}
