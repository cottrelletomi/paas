package com.example.heartratesensordatacollector.producers;

import com.example.heartratesensordatacollector.models.HeartRate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class HeartRateProducer {

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    private static final Logger LOGGER = LoggerFactory.getLogger(HeartRateProducer.class);

    private RabbitTemplate rabbitTemplate;

    public HeartRateProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendHeartRate(HeartRate heartRate){
        LOGGER.info(String.format("HeartRate sent -> %s", heartRate.toString()));
        rabbitTemplate.convertAndSend(exchange, routingKey, heartRate);
    }

}