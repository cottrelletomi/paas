package com.example.emergencynotificationagent.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Value("${rabbitmq.queue.notification.name}")
    private String notificationQueue;

    @Value("${rabbitmq.queue.emergency.name}")
    private String emergencyQueue;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.notification.key}")
    private String routingNotificationKey;

    @Value("${rabbitmq.routing.emergency.key}")
    private String routingEmergencyKey;

    @Bean
    public Queue notificationQueue(){
        return new Queue(notificationQueue);
    }

    @Bean
    public Queue emergencyQueue(){
        return new Queue(emergencyQueue);
    }

    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(exchange);
    }

    // binding between queue and exchange using routing key
    @Bean
    public Binding notificationBinding(){
        return BindingBuilder
                .bind(notificationQueue())
                .to(exchange())
                .with(routingNotificationKey);
    }

    @Bean
    public Binding emergencyBinding(){
        return BindingBuilder
                .bind(emergencyQueue())
                .to(exchange())
                .with(routingEmergencyKey);
    }

    @Bean
    public MessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}