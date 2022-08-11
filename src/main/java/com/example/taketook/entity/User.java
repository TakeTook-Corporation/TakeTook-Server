package com.example.taketook.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Document
public class User {
    @Id
    private String id;

    private String name;
    private String surname;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String password;
    private String avaUrl;
    private Double rating;
    private Set<String> userRatings; // amount of people who left ratings
    private List<String> commentIds;

    @DBRef
    private Set<Role> roles = new HashSet<>();

    public User() {}

    public User(String name, String surname, String email, String phone, String address, String city, String password, String avaUrl, Double rating, Set<String> userRatings, List<String> commentIds) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.password = password;
        this.avaUrl = avaUrl;
        this.rating = rating;
        this.userRatings = userRatings;
        this.commentIds = commentIds;
    }

    public boolean rate(Double rating, String userId) {
        if((rating > 0) && (rating <= 5) && !this.userRatings.contains(userId) && (!Objects.equals(this.id, userId))) {
            this.userRatings.add(userId);
            this.rating = (rating + this.rating) / this.userRatings.size();
            return true;
        }
        return false;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvaUrl() {
        return avaUrl;
    }

    public void setAvaUrl(String avaUrl) {
        this.avaUrl = avaUrl;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Set<String> getUserRatings() {
        return userRatings;
    }

    public void setUserRatings(Set<String> userRatings) {
        this.userRatings = userRatings;
    }

    public List<String> getCommentIds() {
        return commentIds;
    }

    public void setCommentIds(List<String> commentIds) {
        this.commentIds = commentIds;
    }

}
