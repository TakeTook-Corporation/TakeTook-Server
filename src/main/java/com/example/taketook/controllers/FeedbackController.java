package com.example.taketook.controllers;

import com.example.taketook.entity.Feedback;
import com.example.taketook.payload.request.CreateFeedbackRequest;
import com.example.taketook.payload.response.MessageResponse;
import com.example.taketook.repository.FeedbackRepository;
import com.example.taketook.repository.UserRepository;
import com.example.taketook.utils.JwtUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.taketook.utils.ErrorMessages.INCORRECT_JWT;
import static com.example.taketook.utils.Support.getUserId;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    private final FeedbackRepository feedbackRepository;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;

    public FeedbackController(FeedbackRepository feedbackRepository, JwtUtils jwtUtils, UserRepository userRepository) {
        this.feedbackRepository = feedbackRepository;
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createFeedback(
                            @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                            @RequestBody CreateFeedbackRequest createFeedbackRequest) {
        Integer author = getUserId(token, jwtUtils, userRepository);
        if (author == -1) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(INCORRECT_JWT));
        }

        Feedback feedback = new Feedback(
                                            author.toString(),
                                            createFeedbackRequest.getListingId(),
                                            createFeedbackRequest.getStarCount(),
                                            createFeedbackRequest.getText()
                                        );

        return ResponseEntity.ok(feedbackRepository.save(feedback));
    }

    @GetMapping("/listing/{listing}")
    public List<Feedback> getFeedbacksByListing(@PathVariable String listing) {
        return feedbackRepository.findByListingId(listing);
    }

    @GetMapping("/user/{userId}")
    public List<Feedback> getFeedbacksByUser(@PathVariable String userId) {
        return feedbackRepository.findByUserId(userId);
    }
}
