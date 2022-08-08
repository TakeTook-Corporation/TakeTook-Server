package com.example.taketook.repository;

import com.example.taketook.entity.Story;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StoryRepository extends MongoRepository<Story, String> {
}
