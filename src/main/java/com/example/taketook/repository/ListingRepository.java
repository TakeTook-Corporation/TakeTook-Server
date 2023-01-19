package com.example.taketook.repository;

import com.example.taketook.entity.Listing;
import com.example.taketook.entity.User;
import com.example.taketook.utils.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {
    public Optional<Listing> findById(String id);
    public List<Listing> findByAuthor(String author);
    public List<Listing> findByCategory(Category category);
}
