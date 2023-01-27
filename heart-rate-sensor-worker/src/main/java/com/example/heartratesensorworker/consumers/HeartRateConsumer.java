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
        LOGGER.info("------------------------------------------------------------------");
        LOGGER.info(String.format("Received message -> %s", heartRate.toString()));
        User user = checkUserSubscribing(heartRate);
        if (user != null) {
            //LOGGER.info(String.format("Registered user"));
            checkEmergency(heartRate, user);
            storeData(heartRate);
            //testStoreData();
        } else {
            LOGGER.info(String.format("Unknown user"));
        }
    }

    private User checkUserSubscribing(HeartRate heartRate) {
        //LOGGER.info(String.format("Check user subscribing"));
        User user = this.getUser(heartRate.getEmail());
        return user;
    }

    private User getUser(String email) {
        StringBuilder request = new StringBuilder(USER_URL).append("/search")
                .append("?email=").append(email);
        User user = restTemplate.getForObject(request.toString(), User.class);
        return user;
    }

    private void checkEmergency(HeartRate heartRate, User user) {
        int hr = heartRate.getHeart_rate();
        LOGGER.info(user.getFirstname() + ", " + user.getWeight() + "kg, " + user.getAge() + " y, " + hr + " bpm");
        if (user.getGender() == 'M') {
            if(user.getAge() < 26) {
                if(user.getWeight() < 60) {
                    if(hr > 180 || hr < 40) {
                        emergencyProducer.sendMessage(heartRate);
                    }
                } else if(user.getWeight() < 80) {
                    if(hr > 177 || hr < 40) {
                        emergencyProducer.sendMessage(heartRate);
                    }
                } else if(user.getWeight() < 100) {
                    if(hr > 174 || hr < 40) {
                        emergencyProducer.sendMessage(heartRate);
                    }
                } else {
                    if(hr > 172 || hr < 40) {
                        emergencyProducer.sendMessage(heartRate);
                    }
                }
            } else if(user.getAge() < 51) {
                if(user.getWeight() < 60) {
                    if(hr > 168 || hr < 40) {
                        emergencyProducer.sendMessage(heartRate);
                    }
                } else if(user.getWeight() < 80) {
                    if(hr > 165 || hr < 40) {
                        emergencyProducer.sendMessage(heartRate);
                    }
                } else if(user.getWeight() < 100) {
                    if(hr > 163 || hr < 40) {
                        emergencyProducer.sendMessage(heartRate);
                    }
                } else {
                    if(hr > 161 || hr < 40) {
                        emergencyProducer.sendMessage(heartRate);
                    }
                }
            } else if(user.getAge() < 76) {
                if(user.getWeight() < 60) {
                    if(hr > 159 || hr < 40) {
                        emergencyProducer.sendMessage(heartRate);
                    }
                } else if(user.getWeight() < 80) {
                    if(hr > 156 || hr < 40) {
                        emergencyProducer.sendMessage(heartRate);
                    }
                } else if(user.getWeight() < 100) {
                    if(hr > 153 || hr < 40) {
                        emergencyProducer.sendMessage(heartRate);
                    }
                } else {
                    if(hr > 150 || hr < 40) {
                        emergencyProducer.sendMessage(heartRate);
                    }
                }
            } else {
                if (user.getWeight() < 60) {
                    if (hr > 151 || hr < 40) {
                        emergencyProducer.sendMessage(heartRate);
                    }
                } else if (user.getWeight() < 80) {
                    if (hr > 148 || hr < 40) {
                        emergencyProducer.sendMessage(heartRate);
                    }
                } else if (user.getWeight() < 100) {
                    if (hr > 145 || hr < 40) {
                        emergencyProducer.sendMessage(heartRate);
                    }
                } else {
                    if (hr > 143 || hr < 40) {
                        emergencyProducer.sendMessage(heartRate);
                    }
                }
            }
        } else {
            if(user.getAge() < 26) {
                if(user.getWeight() < 60) {
                    if(hr > 174 || hr < 40) {
                        emergencyProducer.sendMessage(heartRate);
                    }
                } else if(user.getWeight() < 80) {
                    if(hr > 172 || hr < 40) {
                        emergencyProducer.sendMessage(heartRate);
                    }
                } else if(user.getWeight() < 100) {
                    if(hr > 169 || hr < 40) {
                        emergencyProducer.sendMessage(heartRate);
                    }
                } else {
                    if(hr > 167 || hr < 40) {
                        emergencyProducer.sendMessage(heartRate);
                    }
                }
            } else if(user.getAge() < 51) {
                if(user.getWeight() < 60) {
                    if(hr > 165 || hr < 40) {
                        emergencyProducer.sendMessage(heartRate);
                    }
                } else if(user.getWeight() < 80) {
                    if(hr > 162 || hr < 40) {
                        emergencyProducer.sendMessage(heartRate);
                    }
                } else if(user.getWeight() < 100) {
                    if(hr > 160 || hr < 40) {
                        emergencyProducer.sendMessage(heartRate);
                    }
                } else {
                    if(hr > 158 || hr < 40) {
                        emergencyProducer.sendMessage(heartRate);
                    }
                }
            } else if(user.getAge() < 76) {
                if(user.getWeight() < 60) {
                    if(hr > 155 || hr < 40) {
                        emergencyProducer.sendMessage(heartRate);
                    }
                } else if(user.getWeight() < 80) {
                    if(hr > 152 || hr < 40) {
                        emergencyProducer.sendMessage(heartRate);
                    }
                } else if(user.getWeight() < 100) {
                    if(hr > 150 || hr < 40) {
                        emergencyProducer.sendMessage(heartRate);
                    }
                } else {
                    if(hr > 147 || hr < 40) {
                        emergencyProducer.sendMessage(heartRate);
                    }
                }
            } else {
                if (user.getWeight() < 60) {
                    if (hr > 148 || hr < 40) {
                        emergencyProducer.sendMessage(heartRate);
                    }
                } else if (user.getWeight() < 80) {
                    if (hr > 145 || hr < 40) {
                        emergencyProducer.sendMessage(heartRate);
                    }
                } else if (user.getWeight() < 100) {
                    if (hr > 142 || hr < 40) {
                        emergencyProducer.sendMessage(heartRate);
                    }
                } else {
                    if (hr > 140 || hr < 40) {
                        emergencyProducer.sendMessage(heartRate);
                    }
                }
            }
        }
    }

    private void storeData(HeartRate heartRate) {
        //LOGGER.info(String.format("Store data"));
        heartRateRepository.save(heartRate);
    }

    /*private void testStoreData() {
        LOGGER.info(String.format("Test store data"));
        List<HeartRate> heartRate = heartRateRepository.findAll();
        for(HeartRate hr: heartRate) {
            LOGGER.info(String.format("Get data stored -> %s", hr.toString()));
        }
    }*/
}
