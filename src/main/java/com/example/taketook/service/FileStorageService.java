package com.example.taketook.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface FileStorageService {
    public void init();
    public void save(MultipartFile file, Path path, String fileName);
    public Resource load(String filename);
}
