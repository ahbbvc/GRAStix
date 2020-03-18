package com.example.ticketservice.Repository;

import com.example.ticketservice.Entity.Linija;
import com.example.ticketservice.Entity.PojedinacnaKarta;
import org.springframework.data.repository.CrudRepository;

public interface PojedinacnaKartaRepository extends CrudRepository<PojedinacnaKarta, Integer> {
}