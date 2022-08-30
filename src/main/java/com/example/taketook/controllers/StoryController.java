package com.example.taketook.controllers;

import com.example.taketook.entity.Story;
import com.example.taketook.payload.response.MessageResponse;
import com.example.taketook.repository.StoryRepository;
import com.example.taketook.service.FileStorageService;
import com.example.taketook.utils.Constants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

import static com.example.taketook.utils.Support.getExtensionFromFile;

@RestController
@RequestMapping("/story")
public class StoryController {
    private final StoryRepository storyRepository;
    private final FileStorageService fileStorageService;

    public StoryController(StoryRepository storyRepository, FileStorageService fileStorageService) {
        this.storyRepository = storyRepository;
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createStory(@RequestParam(value = "files") MultipartFile[] files) {
        Story story = new Story();
        storyRepository.save(story);

        if (files.length == 2) {
            String fileName = story.getId() + "_" + "icon" + "." + getExtensionFromFile(files[0]);
            String iconUrl = uploadFile(Constants.STORY_IMAGE_FOLDER, files[0], fileName);

            fileName = story.getId() + "_" + "info" + "." + getExtensionFromFile(files[1]);
            String infoUrl = uploadFile(Constants.STORY_IMAGE_FOLDER, files[1], fileName);

            story.setIconUrl(iconUrl);
            story.setInfoIconUrl(infoUrl);
            return ResponseEntity.ok(storyRepository.save(story));
        }

        return ResponseEntity.badRequest().body(new MessageResponse("Story was not created"));
    }

    @GetMapping("")
    public ResponseEntity<?> getStories() {
        return ResponseEntity.ok(storyRepository.findAll());
    }

    public String uploadFile(Path path, MultipartFile file, String fileName) {
        try {
            fileStorageService.save(file, path, fileName);
            return Constants.SITE_URI + Constants.GET_FILE_SUB_URL + "stories/" + fileName;
        } catch (Exception e) {
            return null;
        }
    }

}
