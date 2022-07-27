package com.example.taketook.repository;

import com.example.taketook.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {
    public User findById(Long id);
    public List<User> findByAddress(String address);
}
