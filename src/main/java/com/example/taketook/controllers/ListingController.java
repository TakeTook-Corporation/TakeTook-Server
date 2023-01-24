package com.example.taketook.controllers;

import com.example.taketook.entity.Listing;
import com.example.taketook.payload.request.CreateListingRequest;
import com.example.taketook.payload.response.MessageResponse;
import com.example.taketook.repository.ListingRepository;
import com.example.taketook.repository.UserRepository;
import com.example.taketook.service.storage.StorageService;
import com.example.taketook.utils.Category;
import com.example.taketook.utils.JwtUtils;
import com.example.taketook.utils.Support;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.example.taketook.utils.ErrorMessages.*;
import static com.example.taketook.utils.Support.getUserId;
import static com.example.taketook.utils.Support.uploadAvatar;

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
        Integer author = getUserId(token, jwtUtils, userRepository);
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
                                        author.toString(),
                                        createListingRequest.getDot(),
                                        true,
                                        createListingRequest.getCategory()
                                    );
        listingRepository.save(listing);

        if (image != null) {
            listing.setIconLink(uploadAvatar(image, listing.getId(), storageService, Support.ImageType.LISTING));
            listingRepository.save(listing);
        }

        return ResponseEntity.ok(listing);
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
