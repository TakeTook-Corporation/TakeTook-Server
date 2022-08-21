package com.example.taketook.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Automate {
    @Id
    private String id;

    private Double lat;
    private Double lon;
    // private CityEnum city;
    private String address;
    private List<String> dots;

    public Automate() {}

    public Automate(Double lat, Double lon, String address, List<String> dots) {
        this.lat = lat;
        this.lon = lon;
        this.address = address;
        this.dots = dots;
    }

    public String getId() {
        return id;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getDots() {
        return dots;
    }

    public void setDots(List<String> dots) {
        this.dots = dots;
    }
}
