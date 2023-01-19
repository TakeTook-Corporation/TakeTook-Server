package com.example.taketook.entity;


import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "automates")
public class Automate {
    public void setId(Integer id) {
        this.id = id;
    }

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Double lat;
    private Double lon;
    private String address;
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinTable(name = "automate_dots",
            joinColumns = @JoinColumn(name = "automate"),
            inverseJoinColumns = @JoinColumn(name = "dot"))
    private Set<Dot> dots = new HashSet<>();

    public Automate() {}

    public Automate(Double lat, Double lon, String address, Set<Dot> dots) {
        this.lat = lat;
        this.lon = lon;
        this.address = address;
        this.dots = dots;
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

    public Set<Dot> getDots() {
        return dots;
    }

    public void setDots(Set<Dot> dots) {
        this.dots = dots;
    }

    public Integer getId() {
        return id;
    }
}
