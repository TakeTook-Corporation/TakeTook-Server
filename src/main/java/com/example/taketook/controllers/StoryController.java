package com.example.taketook.controllers;

import com.example.taketook.entity.Story;
import com.example.taketook.repository.StoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/story")
public class StoryController {
    private final StoryRepository storyRepository;

    public StoryController(StoryRepository storyRepository) {
        this.storyRepository = storyRepository;
    }

//    @PostMapping("/create")
//    public Story createStory() {
//
//    }

    @GetMapping("")
    public ResponseEntity<?> getStories() {
        return ResponseEntity.ok(storyRepository.findAll());
    }
}
