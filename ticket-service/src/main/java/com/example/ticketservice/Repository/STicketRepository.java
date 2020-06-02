package com.example.ticketservice.Repository;

import com.example.ticketservice.Model.SingleTicket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface STicketRepository extends JpaRepository<SingleTicket, Integer> {

    List<SingleTicket> findByUserId(Integer userId);

    List<SingleTicket> findByRouteId(Integer routeId);

    List<SingleTicket> findByUserIdAndValidated(Integer userId, Boolean validated);

    List<SingleTicket> findByValidated(Boolean validated);
}
