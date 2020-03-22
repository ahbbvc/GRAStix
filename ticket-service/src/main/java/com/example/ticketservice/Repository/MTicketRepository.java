package com.example.ticketservice.Repository;

import com.example.ticketservice.Entity.MonthlyTicket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MTicketRepository extends JpaRepository<MonthlyTicket, Integer> {

}