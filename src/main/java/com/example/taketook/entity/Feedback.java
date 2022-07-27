package com.example.taketook.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Feedback {
    @Id
    private Long id;

    private Long userId;
    private Long listingId;
    private Long starCount;
    private String text;
    // private User author;

    public Feedback() {}

    public Feedback(Long userId, Long listingId, Long starCount, String text) {
        this.userId = userId;
        this.listingId = listingId;
        this.starCount = starCount;
        this.text = text;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getListingId() {
        return listingId;
    }

    public void setListingId(Long listingId) {
        this.listingId = listingId;
    }

    public Long getStarCount() {
        return starCount;
    }

    public void setStarCount(Long starCount) {
        this.starCount = starCount;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
