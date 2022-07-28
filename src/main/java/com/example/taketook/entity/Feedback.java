package com.example.taketook.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Feedback {
    @Id
    private String id;

    private String userId;
    private String listingId;
    private Long starCount;
    private String text;

    public Feedback() {}

    public Feedback(String userId, String listingId, Long starCount, String text) {
        this.userId = userId;
        this.listingId = listingId;
        this.starCount = starCount;
        this.text = text;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getListingId() {
        return listingId;
    }

    public void setListingId(String listingId) {
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
