package com.example.emergencynotificationagent.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;

@Data
@RedisHash("usersRedis")
public class UserRedis implements Serializable {
    @Id
    private String id;
    private String firstname;
    private String lastname;
    @Indexed
    private String email;
    private String password;
    private int age;
    private int weight;
    private String gender;
    private int training_factor;

    public UserRedis(String id, String firstname, String lastname, String email, String password, int age, int weight, String gender, int training_factor) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.age = age;
        this.weight = weight;
        this.gender = gender;
        this.training_factor = training_factor;
    }
}
