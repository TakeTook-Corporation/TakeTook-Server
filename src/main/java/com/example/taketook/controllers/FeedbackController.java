package com.example.taketook.controllers;

import com.example.taketook.entity.Feedback;
import com.example.taketook.payload.request.CreateFeedbackRequest;
import com.example.taketook.repository.FeedbackRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    private final FeedbackRepository feedbackRepository;

    public FeedbackController(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @PostMapping("/create")
    public Feedback createFeedback(@RequestBody CreateFeedbackRequest createFeedbackRequest) {
        Feedback feedback = new Feedback(
                                            createFeedbackRequest.getUserId(),
                                            createFeedbackRequest.getListingId(),
                                            createFeedbackRequest.getStarCount(),
                                            createFeedbackRequest.getText()
                                        );

        return feedbackRepository.save(feedback);
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
