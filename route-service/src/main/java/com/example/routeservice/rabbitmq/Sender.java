package com.example.routeservice.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Sender {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    static final String exchange = "route-queue";

    public void send(Integer routeId) {
        rabbitTemplate.convertAndSend(exchange, routeId.toString());
        System.out.println("Message sent with routeId = " + routeId);
    }
}
