package com.example.heartratesensorworker.consumers;

import com.example.heartratesensorworker.models.HeartRate;
import com.example.heartratesensorworker.producers.EmergencyProducer;
import com.example.heartratesensorworker.repositories.HeartRateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HeartRateConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(HeartRateConsumer.class);
    private EmergencyProducer emergencyProducer;
    private final HeartRateRepository heartRateRepository;

    public HeartRateConsumer(EmergencyProducer emergencyProducer, HeartRateRepository heartRateRepository) {
        this.emergencyProducer = emergencyProducer;
        this.heartRateRepository = heartRateRepository;
    }

    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void consumeMessage(HeartRate heartRate) {
        LOGGER.info(String.format("Received message -> %s", heartRate.toString()));
        checkUserSubscribing(heartRate);
        checkEmergency(heartRate);
        storeData(heartRate);
        testStoreData();
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
        heartRateRepository.save(heartRate);
    }

    private void testStoreData() {
        LOGGER.info(String.format("Test store data"));
        List<HeartRate> heartRate = heartRateRepository.findAll();
        for(HeartRate hr: heartRate) {
            LOGGER.info(String.format("Get data stored -> %s", hr.toString()));
        }
    }

}
