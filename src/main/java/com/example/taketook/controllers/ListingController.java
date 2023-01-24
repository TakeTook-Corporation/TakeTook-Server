package com.example.taketook.controllers;

import com.example.taketook.entity.Listing;
import com.example.taketook.payload.request.CreateListingRequest;
import com.example.taketook.repository.ListingRepository;
import com.example.taketook.utils.Category;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/listing")
public class ListingController {
    private final ListingRepository listingRepository;

    public ListingController(ListingRepository listingRepository) {
        this.listingRepository = listingRepository;
    }

    @PostMapping("/create")
    public Listing createListing(@RequestPart("listing_data") CreateListingRequest createListingRequest,
                                 @RequestParam(required = false) MultipartFile image) {
        Listing listing = new Listing(
                                        createListingRequest.getTitle(),
                                        createListingRequest.getDescription(),
                                        createListingRequest.getAuthor(),
                                        createListingRequest.getDot(),
                                        createListingRequest.getActive(),
                                        createListingRequest.getCategory()
                                    );
        return listingRepository.save(listing);
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
