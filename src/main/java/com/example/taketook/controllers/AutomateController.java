package com.example.taketook.controllers;

import com.example.taketook.entity.Automate;
import com.example.taketook.entity.Dot;
import com.example.taketook.payload.request.PutToSellDotRequest;
import com.example.taketook.payload.request.RentDotRequest;
import com.example.taketook.payload.response.MessageResponse;
import com.example.taketook.repository.AutomateRepository;
import com.example.taketook.repository.DotRepository;
import com.example.taketook.repository.UserRepository;
import com.example.taketook.utils.JwtUtils;
import com.example.taketook.utils.RentType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static com.example.taketook.utils.ErrorMessages.*;
import static com.example.taketook.utils.Support.getUserId;

@RestController
@RequestMapping("/automate")
public class AutomateController {
    private final AutomateRepository automateRepository;
    private final DotRepository dotRepository;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;

    public AutomateController(AutomateRepository automateRepository, DotRepository dotRepository, JwtUtils jwtUtils, UserRepository userRepository) {
        this.automateRepository = automateRepository;
        this.dotRepository = dotRepository;
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
    }

    @PostMapping("/rent")
    public ResponseEntity<?> rent(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                  @RequestBody RentDotRequest rentDotRequest) {
        Integer userId = getUserId(token, jwtUtils, userRepository);
        if (userId == -1) return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new MessageResponse(INCORRECT_JWT));

        Dot dot = dotRepository.findById(rentDotRequest.getDotId())
                            .orElseThrow(() -> new RuntimeException(DOT_NOT_FOUND));

        dot.setListingId(null);
        dot.setFree(false);
        dot.setRentType(RentType.RENT);

        dot.setRentTariff(rentDotRequest.getRentTariff());
        dot.setRentTime(rentDotRequest.getRentTime());

        return ResponseEntity.ok(dotRepository.save(dot));
    }

    @PostMapping("/sell")
    public ResponseEntity<?> putToSell(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                       @RequestBody PutToSellDotRequest putToSellDotRequest) {
        Integer userId = getUserId(token, jwtUtils, userRepository);
        if (userId == -1) return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new MessageResponse(INCORRECT_JWT));

        Dot dot = dotRepository.findById(putToSellDotRequest.getDotId())
                            .orElseThrow(() -> new RuntimeException(DOT_NOT_FOUND));

        dot.setListingId(putToSellDotRequest.getListingId());
        dot.setFree(false);
        dot.setRentType(RentType.SELL);

        dot.setRentTariff(putToSellDotRequest.getRentTariff());
        dot.setRentTime(putToSellDotRequest.getRentTime());

        return ResponseEntity.ok(dotRepository.save(dot));
    }

    @GetMapping("/address/{addr}")
    public Automate getAutomateByAddress(@PathVariable String addr) {
        return automateRepository.findByAddress(addr)
                                    .orElseThrow(() -> new RuntimeException(AUTOMATE_NOT_FOUND));
    }

    @GetMapping("/dots/{automateId}")
    public Set<Dot> getDotsByAutomate(@PathVariable String automateId) {
        Automate automate = automateRepository.findById(automateId)
                                    .orElseThrow(() -> new RuntimeException(AUTOMATE_NOT_FOUND));

        return automate.getDots();
    }
}
