package br.com.fernandoguide.goldenraspberryawards.awardsapi.fixture;

import br.com.fernandoguide.goldenraspberryawards.awardsapi.dto.IntervalAward;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class IntervalAwardFixture {

    public static IntervalAward buid(String producerName,
                                     Integer interval,
                                     Integer previousWin,
                                     Integer followingWin) {
        return new IntervalAward(
            producerName,
            interval,
            previousWin,
            followingWin
        );
    }

}
