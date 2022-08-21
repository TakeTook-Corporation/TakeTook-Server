package com.example.taketook.repository;

import com.example.taketook.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    public Optional<User> findById(String id);
    public Optional<User> findBySurname(String username);
    public Optional<User> findByEmail(String email);
    public Optional<User> findByPhone(String phone);
    public List<User> findByAddress(String address);
    public Boolean existsByEmail(String email);
}
