package com.example.routeservice.Repository;

import com.example.routeservice.Entity.TimeTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeTableRepository extends JpaRepository<TimeTable, Integer> {
}
