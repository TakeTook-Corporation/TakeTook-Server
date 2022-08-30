package com.example.taketook.controllers;

import com.example.taketook.entity.Comment;
import com.example.taketook.entity.Listing;
import com.example.taketook.payload.request.ListingController.CreateCommentRequest;
import com.example.taketook.payload.request.ListingController.CreateListingRequest;
import com.example.taketook.payload.response.MessageResponse;
import com.example.taketook.repository.CommentRepository;
import com.example.taketook.repository.ListingRepository;
import com.example.taketook.repository.UserRepository;
import com.example.taketook.service.FileStorageService;
import com.example.taketook.utils.Category;
import com.example.taketook.utils.Constants;
import com.example.taketook.utils.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static com.example.taketook.utils.Support.getExtensionFromFile;

@RestController
@RequestMapping("/listing")
public class ListingController {
    private final ListingRepository listingRepository;
    private final FileStorageService fileStorageService;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    public ListingController(ListingRepository listingRepository, FileStorageService fileStorageService, CommentRepository commentRepository, UserRepository userRepository, JwtUtils jwtUtils) {
        this.listingRepository = listingRepository;
        this.fileStorageService = fileStorageService;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createListing(@RequestHeader("jwt") String jwt, @RequestPart CreateListingRequest createListingRequest, @RequestParam(value = "files", required = false) MultipartFile[] files) {
        String id = jwtUtils.getIdFromToken(jwt);

        if (!userRepository.existsById(id)) {
            return ResponseEntity.badRequest().body(new MessageResponse("User doesnt exist"));
        }

        Listing listing = new Listing(createListingRequest.getTitle(), createListingRequest.getDescription(), id, createListingRequest.getDot(), createListingRequest.getActive(), createListingRequest.getCategory(), new ArrayList<>(), new ArrayList<>(), createListingRequest.getAutomateIds(), createListingRequest.getDeliveryStatuses());
        Listing saved = listingRepository.save(listing);

        if (files.length != 0) {
            List<String> imageUrls = new ArrayList<>();
            for (int i = 0; i < files.length; i++) {
                String fileName = saved.getId() + "_" + i + "." + getExtensionFromFile(files[i]);
                String imageUrl = uploadFile(Constants.LISTING_IMAGE_FOLDER, files[i], fileName);
                imageUrls.add(imageUrl);
            }
            saved.setImageUrls(imageUrls);
            saved = listingRepository.save(saved);
        }
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/comment")
    public ResponseEntity<?> leaveComment(@RequestHeader("jwt") String jwt, @RequestBody CreateCommentRequest createCommentRequest) {
        String id = jwtUtils.getIdFromToken(jwt);

        if (!userRepository.existsById(id)) {
            return ResponseEntity.badRequest().body(new MessageResponse("User doesnt exist"));
        }

        Listing listing = listingRepository.findById(createCommentRequest.getListingId()).orElseThrow(RuntimeException::new);
        Comment comment = new Comment(createCommentRequest.getText(), id, System.currentTimeMillis());
        String newCommentId = commentRepository.save(comment).getId();
        List<String> commentIds = listing.getCommentIds();

        commentIds.add(newCommentId);
        listing.setCommentIds(commentIds);

        return ResponseEntity.ok(listingRepository.save(listing));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllListings() {
        return ResponseEntity.ok(listingRepository.findAll());
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<?> getListingsByCategory(@PathVariable Category category) {
        return ResponseEntity.ok(listingRepository.findByCategory(category));
    }

    @GetMapping("/author/{author}")
    public ResponseEntity<?> getListingsByAuthor(@PathVariable String author) {
        return ResponseEntity.ok(listingRepository.findByAuthor(author));
    }

    public String uploadFile(Path path, MultipartFile file, String fileName) {
        try {
            fileStorageService.save(file, path, fileName);
            return Constants.SITE_URI + Constants.GET_FILE_SUB_URL + "listings/" + fileName;
        } catch (Exception e) {
            return null;
        }
    }
}
