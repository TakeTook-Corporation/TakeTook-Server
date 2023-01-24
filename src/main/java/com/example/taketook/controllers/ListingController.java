package com.example.taketook.controllers;

import com.example.taketook.entity.Listing;
import com.example.taketook.entity.User;
import com.example.taketook.payload.request.CreateListingRequest;
import com.example.taketook.payload.response.MessageResponse;
import com.example.taketook.repository.ListingRepository;
import com.example.taketook.repository.UserRepository;
import com.example.taketook.service.storage.StorageService;
import com.example.taketook.utils.Category;
import com.example.taketook.utils.JwtUtils;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.example.taketook.utils.ErrorMessages.*;

@RestController
@RequestMapping("/listing")
public class ListingController {
    private final ListingRepository listingRepository;
    private final UserRepository userRepository;
    private final StorageService storageService;
    private final JwtUtils jwtUtils;

    public ListingController(ListingRepository listingRepository,
                             UserRepository userRepository,
                             StorageService storageService,
                             JwtUtils jwtUtils) {
        this.listingRepository = listingRepository;
        this.userRepository = userRepository;
        this.storageService = storageService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createListing(
                                        @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                        @RequestPart("listing_data") CreateListingRequest createListingRequest,
                                        @RequestParam(required = false) MultipartFile image) {
        Integer author = getUserId(token);
        if (author == -1) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(INCORRECT_JWT));
        }

        Listing listing = new Listing(
                                        createListingRequest.getTitle(),
                                        createListingRequest.getPrice(),
                                        BASE_IMAGE_LINK,
                                        createListingRequest.getCity(),
                                        createListingRequest.getUsingAutomate(),
                                        createListingRequest.getDescription(),
                                        token,
                                        createListingRequest.getDot(),
                                        true,
                                        createListingRequest.getCategory()
                                    );
        listingRepository.save(listing);

        if (image != null) {
            listing.setIconLink(uploadAvatar(image, listing.getId()));
            listingRepository.save(listing);
        }

        return ResponseEntity.ok(listing);
    }

    private Integer getUserId(String token) {
        try {
            String email = jwtUtils.getUserNameFromJwtToken(token);
            User user = userRepository.findByEmail(email).orElseThrow(RuntimeException::new);
            return user.getId();
        } catch (SignatureException exception) {
            return -1;
        }
    }

    private String uploadAvatar(MultipartFile image, Integer listingId) {
        String imagePath = IMAGE_HOST_URI + "listing_" + listingId.toString() + ".png";
        storageService.store(image, "listing_" + listingId.toString() + ".png");

        return imagePath;
    }

    @GetMapping("/all")
    public List<Listing> getAllListings() {
        return listingRepository.findAll();
    }

    @GetMapping("/category/{category}")
    public List<Listing> getListingsByCategory(@PathVariable Category category) {
        return listingRepository.findByCategory(category);
    }

    @GetMapping("/author/{author}")
    public List<Listing> getListingsByAuthor(@PathVariable String author) {
        return listingRepository.findByAuthor(author);
    }
}
