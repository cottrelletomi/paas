package com.example.heartratesensorworker.producers;

import com.example.heartratesensorworker.models.HeartRate;
import com.example.heartratesensorworker.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmergencyProducer {

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.emergency.key}")
    private String routingKey;

    private static final Logger LOGGER = LoggerFactory.getLogger(EmergencyProducer.class);

    private RabbitTemplate rabbitTemplate;

    public EmergencyProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(HeartRate heartRate){
        LOGGER.info(String.format("HeartRate sent -> %s", heartRate.toString()));
        rabbitTemplate.convertAndSend(exchange, routingKey, heartRate);
    }

}