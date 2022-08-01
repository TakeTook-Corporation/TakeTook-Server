package com.example.taketook.repository;

import com.example.taketook.entity.Role;
import com.example.taketook.utils.RoleEnum;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
    public Optional<Role> findByRole(RoleEnum role);
}
