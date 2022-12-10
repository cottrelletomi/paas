package com.example.emergencynotificationagent.producers;

import org.example.core.models.UserRedis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class NotificationProducer {

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    /*@Value("${rabbitmq.routing.notification.key}")
    private String routingKey;*/

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationProducer.class);

    private RabbitTemplate rabbitTemplate;

    public NotificationProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(UserRedis userRedis, String routingKey){
        LOGGER.info(String.format("UserRedis sent -> %s", userRedis.toString()));
        rabbitTemplate.convertAndSend(exchange, routingKey, userRedis);
    }

}