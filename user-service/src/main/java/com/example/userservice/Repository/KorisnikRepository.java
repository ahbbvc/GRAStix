package com.example.userservice.Repository;

import com.example.userservice.Entity.Korisnik;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface KorisnikRepository extends CrudRepository<Korisnik, Integer> {
}