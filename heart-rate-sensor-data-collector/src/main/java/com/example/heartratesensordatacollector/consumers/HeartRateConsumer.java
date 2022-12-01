package com.example.heartratesensordatacollector.consumers;

import com.example.heartratesensordatacollector.models.HeartRate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class HeartRateConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(HeartRateConsumer.class);

    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void consumeMessage(HeartRate heartRate){
        LOGGER.info(String.format("Received message -> %s", heartRate.toString()));
    }
}
