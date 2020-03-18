package com.example.ticketservice.Repository;

import com.example.ticketservice.Entity.Linija;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface LinijaRepository extends CrudRepository<Linija, Integer> {
}