package br.com.fernandoguide.goldenraspberryawards.awardsapi.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record AwardIntervalsResponse(
    List<IntervalAward> min,
    List<IntervalAward> max
) {
}
