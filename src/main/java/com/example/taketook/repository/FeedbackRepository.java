package com.example.taketook.repository;

import com.example.taketook.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    public List<Feedback> findByUserId(String userId);
    public List<Feedback> findByListingId(String listingId);
    public Optional<Feedback> findById(String id);
}
