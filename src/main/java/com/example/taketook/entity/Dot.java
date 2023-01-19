package com.example.taketook.entity;

import com.example.taketook.utils.RentTariff;
import com.example.taketook.utils.RentType;

import javax.persistence.*;

@Entity
@Table(name = "dots")
public class Dot {
    public void setId(Integer id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String automateId;
    private String listingId; // TODO: make nullable
    private RentTariff rentTariff;
    private RentType rentType;
    private Long rentTime; // TODO: make nullable
    private Boolean free;

    public Dot() {}

    public Dot(String automateId, String listingId, RentTariff rentTariff, RentType rentType, Long rentTime, Boolean free) {
        this.automateId = automateId;
        this.listingId = listingId;
        this.rentTariff = rentTariff;
        this.rentType = rentType;
        this.rentTime = rentTime;
        this.free = free;
    }

    public String getAutomateId() {
        return automateId;
    }

    public void setAutomateId(String automateId) {
        this.automateId = automateId;
    }

    public String getListingId() {
        return listingId;
    }

    public void setListingId(String listingId) {
        this.listingId = listingId;
    }

    public RentTariff getRentTariff() {
        return rentTariff;
    }

    public void setRentTariff(RentTariff rentTariff) {
        this.rentTariff = rentTariff;
    }

    public RentType getRentType() {
        return rentType;
    }

    public void setRentType(RentType rentType) {
        this.rentType = rentType;
    }

    public Long getRentTime() {
        return rentTime;
    }

    public void setRentTime(Long rentTime) {
        this.rentTime = rentTime;
    }

    public Boolean getFree() {
        return free;
    }

    public void setFree(Boolean free) {
        this.free = free;
    }

    public Integer getId() {
        return id;
    }
}
