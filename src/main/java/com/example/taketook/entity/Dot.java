package com.example.taketook.entity;

import com.example.taketook.utils.RentTariff;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Dot {
    @Id
    private Long id;

    private Long automateId;
    private Long listingId; // TODO: make nullable
    private RentTariff rentTariff;
    private Long rentTime; // TODO: make nullable

    public Dot() {}

    public Dot(Long automateId, Long listingId, RentTariff rentTariff, Long rentTime) {
        this.automateId = automateId;
        this.listingId = listingId;
        this.rentTariff = rentTariff;
        this.rentTime = rentTime;
    }

    public Long getAutomateId() {
        return automateId;
    }

    public void setAutomateId(Long automateId) {
        this.automateId = automateId;
    }

    public Long getListingId() {
        return listingId;
    }

    public void setListingId(Long listingId) {
        this.listingId = listingId;
    }

    public RentTariff getRentTariff() {
        return rentTariff;
    }

    public void setRentTariff(RentTariff rentTariff) {
        this.rentTariff = rentTariff;
    }

    public Long getRentTime() {
        return rentTime;
    }

    public void setRentTime(Long rentTime) {
        this.rentTime = rentTime;
    }
}
