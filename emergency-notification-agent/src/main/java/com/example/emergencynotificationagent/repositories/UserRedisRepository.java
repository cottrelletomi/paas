package com.example.emergencynotificationagent.repositories;

import com.example.emergencynotificationagent.models.UserRedis;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRedisRepository extends CrudRepository<UserRedis, String> {}
