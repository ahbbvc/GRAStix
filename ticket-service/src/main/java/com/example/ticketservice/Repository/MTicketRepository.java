package com.example.ticketservice.Repository;

import com.example.ticketservice.Model.MonthlyTicket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MTicketRepository extends JpaRepository<MonthlyTicket, Integer> {

    List<MonthlyTicket> findByUserIdAndValidated(Integer userId, Boolean validated);

    List<MonthlyTicket> findByUserId(Integer userId);

    List<MonthlyTicket> findByValidated(Boolean validated);
}