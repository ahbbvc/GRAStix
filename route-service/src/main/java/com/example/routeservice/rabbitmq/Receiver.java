package com.example.routeservice.rabbitmq;

import com.example.routeservice.repository.RouteRepository;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import static java.lang.Integer.parseInt;

@RabbitListener(queues = "ticket-queue")
public class Receiver {

    @Autowired
    private RouteRepository routeRepository;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @RabbitHandler
    public void receive(String message) {
        try {
            String status = message.split(" ")[0];
            Integer routeId = parseInt(message.split(" ")[1]);
            System.out.println("Received message with status: " + message);

            if (status.equals("Ok")) {
                routeRepository.deleteById(routeId);
                messagingTemplate.convertAndSend("/topic/notification", "Success. Route is deleted.");
            }
        } catch (Exception e) {
            System.out.println("Error! " + e);
        }

    }

}
