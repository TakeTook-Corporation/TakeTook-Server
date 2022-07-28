package com.example.taketook.repository;

import com.example.taketook.entity.Listing;
import com.example.taketook.entity.User;
import com.example.taketook.utils.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ListingRepository extends MongoRepository<Listing, String> {
    public Optional<Listing> findById(String id);
    public List<Listing> findByAuthor(String author);
    public List<Listing> findByCategory(Category category);
}
