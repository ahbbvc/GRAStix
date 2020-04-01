package com.example.ticketservice.Service;


import com.example.ticketservice.Model.MonthlyTicket;
import com.example.ticketservice.Model.SingleTicket;
import com.example.ticketservice.Repository.MTicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MTicketService {
    private final MTicketRepository mTicketRepository;

    public MTicketService(MTicketRepository mTicketRepository) {
        this.mTicketRepository = mTicketRepository;
    }
    public List<MonthlyTicket> findAll(){
        return mTicketRepository.findAll();
    }
    public MonthlyTicket newMTicket(MonthlyTicket mt){
        return mTicketRepository.save(mt);
    }
    public MonthlyTicket findById(Integer id){
        return mTicketRepository.findById(id).orElseThrow();
    }
    public List<MonthlyTicket> findUserMickets(Integer userId){
        return mTicketRepository.findByUserId(userId);
    }
    public List<MonthlyTicket> findValidatedMTickets (Boolean validated){
        return mTicketRepository.findByValidated(validated);
    }
    public List<MonthlyTicket> findValidatedUserMTickets(Integer user_id, Boolean validated){
        return mTicketRepository.findByUserIdAndValidated(user_id, validated);
    }
    public Optional<MonthlyTicket> validateMTicket(Integer id){
        return mTicketRepository.findById(id)
                .map(mt -> {
                    mt.setValidated(Boolean.TRUE);
                    return mTicketRepository.save(mt);
                });
    }
}
