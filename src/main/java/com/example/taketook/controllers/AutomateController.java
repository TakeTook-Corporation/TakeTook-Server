package com.example.taketook.controllers;

import com.example.taketook.entity.Automate;
import com.example.taketook.entity.Dot;
import com.example.taketook.payload.request.AutomateController.AddAutomateRequest;
import com.example.taketook.payload.request.AutomateController.PutToSellDotRequest;
import com.example.taketook.payload.request.AutomateController.RentDotRequest;
import com.example.taketook.payload.response.MessageResponse;
import com.example.taketook.repository.AutomateRepository;
import com.example.taketook.repository.DotRepository;
import com.example.taketook.utils.RentType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/automate")
public class AutomateController {
    private final AutomateRepository automateRepository;
    private final DotRepository dotRepository;

    public AutomateController(AutomateRepository automateRepository, DotRepository dotRepository) {
        this.automateRepository = automateRepository;
        this.dotRepository = dotRepository;
    }

    @PostMapping("/rent")
    public ResponseEntity<?> rent(@RequestBody RentDotRequest rentDotRequest) {
        try {
            Dot dot = dotRepository.findById(rentDotRequest.getDotId()).orElseThrow(() -> new RuntimeException("Dot not found"));
            dot.setListingId(null);
            dot.setFree(false);
            dot.setRentType(RentType.RENT);
            dot.setRentTariff(rentDotRequest.getRentTariff());
            dot.setRentTime(rentDotRequest.getRentTime());
            return ResponseEntity.ok(dotRepository.save(dot));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @PostMapping("/sell")
    public ResponseEntity<?> putToSell(@RequestBody PutToSellDotRequest putToSellDotRequest) {
        try {
            Dot dot = dotRepository.findById(putToSellDotRequest.getDotId()).orElseThrow(() -> new RuntimeException("Dot not found"));
            dot.setListingId(putToSellDotRequest.getListingId());
            dot.setFree(false);
            dot.setRentType(RentType.SELL);
            dot.setRentTariff(putToSellDotRequest.getRentTariff());
            dot.setRentTime(putToSellDotRequest.getRentTime());
            return ResponseEntity.ok(dotRepository.save(dot));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @PostMapping("/addAutomate")
    public ResponseEntity<?> addAutomate(@RequestBody AddAutomateRequest addAutomateRequest) {
        Automate automate = new Automate(addAutomateRequest.getLat(), addAutomateRequest.getLon(), addAutomateRequest.getAddress(), new ArrayList<>());
        Automate savedAutomate = automateRepository.save(automate);
        List<String> dotIds = new ArrayList<>();
        for (long i = 0; i < addAutomateRequest.getDotCount(); i++) {
            Dot dot = new Dot(savedAutomate.getId(), null, null, null, null, true);
            dotIds.add(dotRepository.save(dot).getId());
        }
        savedAutomate.setDots(dotIds);
        return ResponseEntity.ok(automateRepository.save(savedAutomate));
    }

    @GetMapping("/address/{addr}")
    public ResponseEntity<?> getAutomateByAddress(@PathVariable String addr) {
        try {
            Automate automate = automateRepository.findByAddress(addr).orElseThrow(() -> new RuntimeException("Automate not found"));
            return ResponseEntity.ok(automate);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @GetMapping("/dots/{automateId}")
    public ResponseEntity<?> getDotsByAutomate(@PathVariable String automateId) {
        try {
            Automate automate = automateRepository.findById(automateId).orElseThrow(() -> new RuntimeException("Automate not found"));
            List<String> dotIds = automate.getDots();
            List<Dot> dots = new ArrayList<>();
            for (String dotId : dotIds) {
                Dot dot = dotRepository.findById(dotId).orElseThrow(() -> new RuntimeException("Dot not found"));
                dots.add(dot);
            }
            return ResponseEntity.ok(dots);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
}
