package com.example.taketook.entity;

import com.example.taketook.utils.Category;

import javax.persistence.*;

@Entity
@Table(name = "listings")
public class Listing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private Integer price;
    private String iconLink;
    private String city;
    private Boolean usingAutomate;
    private String description;
    private String author;
    private String dot;
    private Boolean active;
    private Category category;

    public Listing() {}

    public Listing(String title, Integer price,
                   String iconLink, String city,
                   Boolean usingAutomate, String description,
                   String author, String dot,
                   Boolean active, Category category) {
        this.title = title;
        this.price = price;
        this.iconLink = iconLink;
        this.city = city;
        this.usingAutomate = usingAutomate;
        this.description = description;
        this.author = author;
        this.dot = dot;
        this.active = active;
        this.category = category;
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getIconLink() {
        return iconLink;
    }

    public void setIconLink(String iconLink) {
        this.iconLink = iconLink;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Boolean getUsingAutomate() {
        return usingAutomate;
    }

    public void setUsingAutomate(Boolean usingAutomate) {
        this.usingAutomate = usingAutomate;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
