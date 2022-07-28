package com.example.taketook.repository;

import com.example.taketook.entity.Automate;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AutomateRepository extends MongoRepository<Automate, String> {
    public Optional<Automate> findByAddress(String address);
    public Optional<Automate> findById(String id);
}
