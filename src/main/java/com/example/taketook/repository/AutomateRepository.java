package com.example.taketook.repository;

import com.example.taketook.entity.Automate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AutomateRepository extends JpaRepository<Automate, Long> {
    public Optional<Automate> findByAddress(String address);
    public Optional<Automate> findById(String id);
}
