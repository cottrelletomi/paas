package com.example.heartratesensorworker.consumers;

import com.example.heartratesensorworker.models.HeartRate;
import com.example.heartratesensorworker.models.User;
import com.example.heartratesensorworker.producers.EmergencyProducer;
import com.example.heartratesensorworker.repositories.HeartRateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class HeartRateConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(HeartRateConsumer.class);
    private EmergencyProducer emergencyProducer;
    private final HeartRateRepository heartRateRepository;

    @Value("${user.url}")
    private String USER_URL;

    private RestTemplate restTemplate;

    public HeartRateConsumer(EmergencyProducer emergencyProducer, HeartRateRepository heartRateRepository, RestTemplateBuilder restTemplateBuilder) {
        this.emergencyProducer = emergencyProducer;
        this.heartRateRepository = heartRateRepository;
        this.restTemplate = restTemplateBuilder.build();
    }

    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void consumeMessage(HeartRate heartRate) {
        LOGGER.info(String.format("Received message -> %s", heartRate.toString()));
        //checkUserSubscribing(heartRate);
        checkEmergency(heartRate);
        //storeData(heartRate);
        //testStoreData();
    }

    private void checkUserSubscribing(HeartRate heartRate) {
        LOGGER.info(String.format("Check user subscribing"));
        User user = this.getUser(heartRate.getEmail());
        LOGGER.info(String.format(user.toString()));
    }

    private User getUser(String email) {
        StringBuilder request = new StringBuilder(USER_URL).append("/search")
                .append("?email=").append(email);
        User user = restTemplate.getForObject(request.toString(), User.class);
        return user;
    }

    private void checkEmergency(HeartRate heartRate) {
        LOGGER.info(String.format("Check emergency"));
        emergencyProducer.sendMessage(heartRate);
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
