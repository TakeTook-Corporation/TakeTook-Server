package com.example.taketook.utils;

import com.example.taketook.entity.User;
import com.example.taketook.repository.UserRepository;
import com.example.taketook.service.storage.StorageService;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.web.multipart.MultipartFile;

import static com.example.taketook.utils.ErrorMessages.IMAGE_HOST_URI;

public class Support {
    public static Integer getUserId(String token, JwtUtils jwtUtils, UserRepository userRepository) {
        try {
            String email = jwtUtils.getUserNameFromJwtToken(token);
            User user = userRepository.findByEmail(email).orElseThrow(RuntimeException::new);
            return user.getId();
        } catch (SignatureException exception) {
            return -1;
        }
    }

    public static String uploadAvatar(MultipartFile image, Integer listingId, StorageService storageService) {
        String imagePath = IMAGE_HOST_URI + "listing_" + listingId.toString() + ".png";
        storageService.store(image, "listing_" + listingId.toString() + ".png");

        return imagePath;
    }
}
