package com.example.taketook.repository;

import com.example.taketook.entity.Dot;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface DotRepository extends MongoRepository<Dot, String > {
    public Optional<Dot> findById(String id);
}
