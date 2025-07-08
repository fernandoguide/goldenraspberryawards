package br.com.fernandoguide.goldenraspberryawards.awardsapi.controllers;

import br.com.fernandoguide.goldenraspberryawards.awardsapi.dto.AwardIntervalsResponse;
import br.com.fernandoguide.goldenraspberryawards.awardsapi.services.AwardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/awards")
public class AwardController {

    private final AwardService awardService;

    public AwardController(AwardService awardService) {
        this.awardService = awardService;
    }

    @GetMapping("/producer-intervals")
    public ResponseEntity<AwardIntervalsResponse> getProducerAwardIntervals() {
        return ResponseEntity.ok(awardService.getProducerAwardIntervals());
    }
}
