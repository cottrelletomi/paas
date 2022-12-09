package com.example.emergencynotificationagent.consumers;

import com.example.emergencynotificationagent.models.HeartRate;
import com.example.emergencynotificationagent.models.User;
import com.example.emergencynotificationagent.models.UserRedis;
import com.example.emergencynotificationagent.producers.NotificationProducer;
import com.example.emergencynotificationagent.repositories.UserRedisRepository;
import com.example.emergencynotificationagent.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmergencyConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmergencyConsumer.class);
    private NotificationProducer notificationProducer;
    private UserRepository userRepository;
    private UserRedisRepository userRedisRepository;

    public EmergencyConsumer(NotificationProducer notificationProducer, UserRepository userRepository, UserRedisRepository userRedisRepository) {
        this.notificationProducer = notificationProducer;
        this.userRepository = userRepository;
        this.userRedisRepository = userRedisRepository;
    }

    @RabbitListener(queues = {"${rabbitmq.queue.emergency.name}"})
    public void consumeMessage(HeartRate heartRate) {
        LOGGER.info(String.format("Received message -> %s", heartRate.toString()));
        coachMobileNotifying(heartRate);
    }

    private void coachMobileNotifying(HeartRate heartRate) {
        LOGGER.info(String.format("Coach mobile notifying"));
        LOGGER.info(String.format("Received message -> %s", heartRate.toString()));
        Optional<User> user = this.userRepository.findUserByEmail(heartRate.getEmail());
        if (user.isPresent()) {
            User u = user.get();
            UserRedis ur = new UserRedis(Integer.toString(u.getId()),
                    u.getFirstname(), u.getLastname(),
                    u.getEmail(), u.getPassword(),
                    u.getAge(), u.getWeight(),
                    Character.toString(u.getGender()), u.getTraining_factor());
            userRedisRepository.save(ur);
            System.out.println("Saved in redis >>>>>>> " + ur);
            UserRedis res = userRedisRepository.findById(Integer.toString(u.getId())).get();
            System.out.println("Find in redis >>>>>>> " + res);
            notificationProducer.sendMessage(res);
            System.out.println("Send to notification queue");

        } else {
            System.out.println("NOOOOOOO");
        }
    }
}
