package com.example.taketook.utils;

import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

public class Support {
    public static String getExtensionFromFile(MultipartFile file) {
        return Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[1];
    }
}
