package com.example.taketook.repository;

import com.example.taketook.entity.Dot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DotRepository extends JpaRepository<Dot, Long > {
    public Optional<Dot> findById(String id);
}
