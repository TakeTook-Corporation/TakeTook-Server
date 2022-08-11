package com.example.taketook.entity;

import com.example.taketook.utils.Category;
import com.example.taketook.utils.ListingDeliveryStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

@Document
public class Listing {
    @Id
    private String id;

    private String title;
    private String description;
    private String author;
    private String dot;
    private Boolean active;
    private Category category;
    private List<String> imageUrls;
    private List<String> commentIds;
    private List<String> wantedAutomates;
    private Set<ListingDeliveryStatus> deliveryStatuses;

    public Listing() {}

    public Listing(String title, String description, String author, String dot, Boolean active, Category category, List<String> imageUrls, List<String> commentIds, List<String> wantedAutomates, Set<ListingDeliveryStatus> deliveryStatuses) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.dot = dot;
        this.active = active;
        this.category = category;
        this.imageUrls = imageUrls;
        this.commentIds = commentIds;
        this.wantedAutomates = wantedAutomates;
        this.deliveryStatuses = deliveryStatuses;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDot() {
        return dot;
    }

    public void setDot(String dot) {
        this.dot = dot;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public List<String> getCommentIds() {
        return commentIds;
    }

    public void setCommentIds(List<String> commentIds) {
        this.commentIds = commentIds;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public Set<ListingDeliveryStatus> getDeliveryStatuses() {
        return deliveryStatuses;
    }

    public void setDeliveryStatuses(Set<ListingDeliveryStatus> deliveryStatuses) {
        this.deliveryStatuses = deliveryStatuses;
    }

    public List<String> getWantedAutomates() {
        return wantedAutomates;
    }

    public void setWantedAutomates(List<String> wantedAutomates) {
        this.wantedAutomates = wantedAutomates;
    }
}
