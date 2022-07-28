package com.example.taketook.controllers;

import com.example.taketook.entity.Automate;
import com.example.taketook.entity.Dot;
import com.example.taketook.payload.request.PutToSellDotRequest;
import com.example.taketook.payload.request.RentDotRequest;
import com.example.taketook.repository.AutomateRepository;
import com.example.taketook.repository.DotRepository;
import com.example.taketook.utils.RentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/automate")
public class AutomateController {
    // TODO: test!

    @Autowired
    private AutomateRepository automateRepository;

    @Autowired
    private DotRepository dotRepository;

    @PostMapping("/rent")
    public Dot rent(@RequestBody RentDotRequest rentDotRequest) {
        Dot dot = dotRepository.findById(rentDotRequest.getDotId()).orElseThrow(() -> new RuntimeException("Dot not found"));
        dot.setListingId(null);
        dot.setFree(false);
        dot.setRentType(RentType.RENT);
        dot.setRentTariff(rentDotRequest.getRentTariff());
        dot.setRentTime(rentDotRequest.getRentTime());
        return dotRepository.save(dot);
    }

    @PostMapping("/sell")
    public Dot putToSell(@RequestBody PutToSellDotRequest putToSellDotRequest) {
        Dot dot = dotRepository.findById(putToSellDotRequest.getDotId()).orElseThrow(() -> new RuntimeException("Dot not found"));
        dot.setListingId(putToSellDotRequest.getListingId());
        dot.setFree(false);
        dot.setRentType(RentType.SELL);
        dot.setRentTariff(putToSellDotRequest.getRentTariff());
        dot.setRentTime(putToSellDotRequest.getRentTime());
        return dotRepository.save(dot);
    }

//    @PostMapping("/pay")
//    public Dot pay(@RequestBody String listingId) {
//
//    }

    @GetMapping("/address/{addr}")
    public Automate getAutomateByAddress(@PathVariable String addr) {
        return automateRepository.findByAddress(addr).orElseThrow(() -> new RuntimeException("Automate not found"));
    }

    @GetMapping("/dots/{automateId}")
    public List<Dot> getDotsByAutomate(@PathVariable String automateId) {
        Automate automate = automateRepository.findById(automateId).orElseThrow(() -> new RuntimeException("Automate not found"));
        List<String> dotIds = automate.getDots();
        List<Dot> dots = new ArrayList<>();
        for (String dotId : dotIds) {
            Dot dot = dotRepository.findById(dotId).orElseThrow(() -> new RuntimeException("Dot not found"));
            dots.add(dot);
        }
        return dots;
    }
}
