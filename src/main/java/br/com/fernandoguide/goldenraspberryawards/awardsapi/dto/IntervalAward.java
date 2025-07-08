package br.com.fernandoguide.goldenraspberryawards.awardsapi.dto;

import lombok.Builder;

@Builder
public record IntervalAward(
    String producer,
    Integer interval,
    Integer previousWin,
    Integer followingWin) {
}
