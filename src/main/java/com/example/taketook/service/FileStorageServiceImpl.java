package com.example.taketook.service;

import com.example.taketook.config.AuthTokenFilter;
import com.example.taketook.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FileStorageServiceImpl implements FileStorageService {
    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);


    @Override
    public void init() {
        try {
            Files.createDirectory(Constants.BASE_IMAGE_FOLDER);
        } catch (IOException e) {
            throw new RuntimeException("Error occurred: " + e.getMessage());
        }
    }

    @Override
    public void save(MultipartFile file, Path path, String fileName) {
        try {
            Files.copy(file.getInputStream(), path.resolve(fileName));
        } catch (Exception e) {
            throw new RuntimeException("Error occurred: " + e.getMessage());
        }
    }

    @Override
    public Resource load(String filename) {
        try {
            Path file = Constants.BASE_IMAGE_FOLDER.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Error occurred");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error occurred: " + e.getMessage());
        }
    }
}
