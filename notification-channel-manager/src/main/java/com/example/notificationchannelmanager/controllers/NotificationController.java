package com.example.notificationchannelmanager.controllers;

import org.example.core.models.UserRedis;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notification")
public class NotificationController {

    private RabbitTemplate rabbitTemplate;

    public NotificationController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("/{id}")
    public UserRedis getUser(@PathVariable("id") int id) {
        System.out.println("id: " + id);
        UserRedis user = (UserRedis) rabbitTemplate.receiveAndConvert("notification_queue");
        return user;
    }
}