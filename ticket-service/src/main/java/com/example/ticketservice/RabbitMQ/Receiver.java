package com.example.ticketservice.RabbitMQ;

import com.example.ticketservice.Model.SingleTicket;
import com.example.ticketservice.Repository.STicketRepository;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@RabbitListener(queues = "route-queue")
public class Receiver {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Autowired
    private STicketRepository sTicketRepository;

    @RabbitHandler
    public void receive(String message) {
            Integer routeId = Integer.parseInt(message);
            System.out.println("Received message = " + routeId);
            List<SingleTicket> singleTickets = sTicketRepository.findByRouteId(routeId);
            List<SingleTicket> deletedTickets = new ArrayList<>();
        try {
            for(SingleTicket st : singleTickets) {
                Integer singleTicketId = st.getId();
                deletedTickets.add(sTicketRepository.findById(singleTicketId).orElseThrow());
                sTicketRepository.deleteById(singleTicketId);
            }

            rabbitTemplate.convertAndSend(RabbitMQConfig.queueName, "Ok " + routeId);
            System.out.println("Sent message with status: Ok " + routeId);

        } catch (Exception e) {
            for(SingleTicket st : deletedTickets) {
                sTicketRepository.save(st);
            }
            rabbitTemplate.convertAndSend(RabbitMQConfig.queueName, "Error " + routeId);
            System.out.println("Sent message with status: Error " + routeId);
        }
    }

}
