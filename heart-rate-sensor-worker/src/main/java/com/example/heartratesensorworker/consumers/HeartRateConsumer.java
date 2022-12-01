package com.example.heartratesensorworker.consumers;

import com.example.heartratesensorworker.models.HeartRate;
import com.example.heartratesensorworker.producers.EmergencyProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class HeartRateConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(HeartRateConsumer.class);
    private EmergencyProducer emergencyProducer;

    public HeartRateConsumer(EmergencyProducer emergencyProducer) {
        this.emergencyProducer = emergencyProducer;
    }

    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void consumeMessage(HeartRate heartRate) {
        LOGGER.info(String.format("Received message -> %s", heartRate.toString()));
        checkUserSubscribing(heartRate);
        checkEmergency(heartRate);
        storeData(heartRate);
    }

    private void checkUserSubscribing(HeartRate heartRate) {
        LOGGER.info(String.format("Check user subscribing"));
    }

    private void checkEmergency(HeartRate heartRate) {
        LOGGER.info(String.format("Check emergency"));
        //emergencyProducer.sendMessage(heartRate);
    }

    private void storeData(HeartRate heartRate) {
        LOGGER.info(String.format("Store data"));
    }

}
