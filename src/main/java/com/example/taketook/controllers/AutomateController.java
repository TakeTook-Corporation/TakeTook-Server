package com.example.taketook.controllers;

import com.example.taketook.entity.Automate;
import com.example.taketook.entity.Dot;
import com.example.taketook.payload.request.PutToSellDotRequest;
import com.example.taketook.payload.request.RentDotRequest;
import com.example.taketook.repository.AutomateRepository;
import com.example.taketook.repository.DotRepository;
import com.example.taketook.utils.RentType;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static com.example.taketook.utils.ErrorMessages.AUTOMATE_NOT_FOUND;
import static com.example.taketook.utils.ErrorMessages.DOT_NOT_FOUND;

@RestController
@RequestMapping("/automate")
public class AutomateController {
    // TODO: test!
    private final AutomateRepository automateRepository;
    private final DotRepository dotRepository;

    public AutomateController(AutomateRepository automateRepository, DotRepository dotRepository) {
        this.automateRepository = automateRepository;
        this.dotRepository = dotRepository;
    }

    @PostMapping("/rent")
    public Dot rent(@RequestBody RentDotRequest rentDotRequest) {
        Dot dot = dotRepository.findById(rentDotRequest.getDotId())
                            .orElseThrow(() -> new RuntimeException(DOT_NOT_FOUND));

        dot.setListingId(null);
        dot.setFree(false);
        dot.setRentType(RentType.RENT);

        dot.setRentTariff(rentDotRequest.getRentTariff());
        dot.setRentTime(rentDotRequest.getRentTime());

        return dotRepository.save(dot);
    }

    @PostMapping("/sell")
    public Dot putToSell(@RequestBody PutToSellDotRequest putToSellDotRequest) {
        Dot dot = dotRepository.findById(putToSellDotRequest.getDotId())
                            .orElseThrow(() -> new RuntimeException(DOT_NOT_FOUND));

        dot.setListingId(putToSellDotRequest.getListingId());
        dot.setFree(false);
        dot.setRentType(RentType.SELL);

        dot.setRentTariff(putToSellDotRequest.getRentTariff());
        dot.setRentTime(putToSellDotRequest.getRentTime());

        return dotRepository.save(dot);
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
