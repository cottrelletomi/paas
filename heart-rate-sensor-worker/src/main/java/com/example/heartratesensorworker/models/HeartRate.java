package com.example.heartratesensorworker.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class HeartRate {
    private String email;
    private int heart_rate;
}