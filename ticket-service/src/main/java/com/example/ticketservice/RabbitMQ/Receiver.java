package com.example.ticketservice.RabbitMQ;

import com.example.ticketservice.Model.SingleTicket;
import com.example.ticketservice.Service.STicketService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RabbitListener(queues = "route-queue")
public class Receiver {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private STicketService sTicketService;

    private String topicExchangeName = "ticket-exchange";

    @RabbitHandler
    public void receive(String message) {
        try {
            Integer routeId = Integer.parseInt(message);
            System.out.println("Consuming Message = " + routeId);
            List<SingleTicket> singleTickets = sTicketService.findSticketsByRoute(routeId);

            for(int i=0; i<singleTickets.size(); i++) {
                Integer singleTicketId = singleTickets.get(i).getId();
                ResponseEntity<Object> obj = sTicketService.deleteSTicket(singleTicketId);
                if(obj.getStatusCode() == HttpStatus.OK)
                    rabbitTemplate.convertAndSend(topicExchangeName, "Ok " + singleTicketId);
                else {
                    rabbitTemplate.convertAndSend(topicExchangeName, "Error " + singleTicketId);
                }
            }

        } catch (Exception e) {

        }
    }

}
