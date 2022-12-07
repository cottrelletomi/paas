package com.example.heartratesensorworker.models;

import lombok.Data;

@Data
public class User {
    private int id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private int age;
    private int weight;
    private char gender;
    private int training_factor;
}