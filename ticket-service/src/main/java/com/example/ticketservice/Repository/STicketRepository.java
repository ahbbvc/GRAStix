package com.example.ticketservice.Repository;
import com.example.ticketservice.Entity.SingleTicket;

import org.springframework.data.jpa.repository.JpaRepository;
public interface STicketRepository extends JpaRepository<SingleTicket, Integer> {

}
