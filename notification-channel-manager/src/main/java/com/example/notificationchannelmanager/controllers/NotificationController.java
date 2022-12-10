package com.example.notificationchannelmanager.controllers;

import com.example.notificationchannelmanager.repositories.UserRedisRepository;
import org.example.core.models.User;
import org.example.core.models.UserRedis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/v1/notification")
public class NotificationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationController.class);

    private RabbitTemplate rabbitTemplate;

    @Value("${user.url}")
    private String USER_URL;

    private RestTemplate restTemplate;

    private UserRedisRepository userRedisRepository;

    public NotificationController(RabbitTemplate rabbitTemplate, RestTemplateBuilder restTemplateBuilder, UserRedisRepository userRedisRepository) {
        this.rabbitTemplate = rabbitTemplate;
        this.restTemplate = restTemplateBuilder.build();
        this.userRedisRepository = userRedisRepository;
    }

    @GetMapping("/{id}")
    public User notification(@PathVariable("id") int id) {
        LOGGER.info(String.format("Check notification -> notification_coach_%d", id));
        User user = (User) rabbitTemplate.receiveAndConvert("notification_coach_" + id);
        return user;
    }

    private User getUser(String email) {
        StringBuilder request = new StringBuilder(USER_URL).append("/search")
                .append("?email=").append(email);
        User user = restTemplate.getForObject(request.toString(), User.class);
        return user;
    }
    @GetMapping("/subscribe")
    public User subscribe(@RequestParam("email") String email) {
        LOGGER.info(String.format("Subscribe notification -> %s", email));
        System.out.println(email);
        User user = this.getUser(email);
        UserRedis u = new UserRedis(Integer.toString(user.getId()),
                user.getFirstname(), user.getLastname(),
                user.getEmail(), user.getPassword(),
                user.getAge(), user.getWeight(),
                Character.toString(user.getGender()), user.getTraining_factor());
        userRedisRepository.save(u);

        System.out.println("Saved in redis >>>>>>> " + u);
        UserRedis res = userRedisRepository.findById(Integer.toString(user.getId())).get();
        System.out.println("Find in redis >>>>>>> " + res);

        return user;
    }
}