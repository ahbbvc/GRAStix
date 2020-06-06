package com.example.routeservice.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;


public class Sender {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void send(Integer routeId) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.queueName, routeId.toString());
        messagingTemplate.convertAndSend("/topic/notification", "Delete request sent.");
        System.out.println("Message sent with routeId = " + routeId);
    }
}
