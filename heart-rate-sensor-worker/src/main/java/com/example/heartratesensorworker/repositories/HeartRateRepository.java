package com.example.heartratesensorworker.repositories;

import com.example.heartratesensorworker.models.HeartRate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeartRateRepository extends MongoRepository<HeartRate, String> {
}
