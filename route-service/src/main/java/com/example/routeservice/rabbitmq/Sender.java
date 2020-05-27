package com.example.routeservice.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;


public class Sender {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send(Integer routeId) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.queueName, routeId.toString());
        System.out.println("Message sent with routeId = " + routeId);
    }
}
