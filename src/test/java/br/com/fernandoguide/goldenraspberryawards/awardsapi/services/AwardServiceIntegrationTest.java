package br.com.fernandoguide.goldenraspberryawards.awardsapi.services;

import br.com.fernandoguide.goldenraspberryawards.awardsapi.dto.AwardIntervalsResponse;
import br.com.fernandoguide.goldenraspberryawards.awardsapi.dto.IntervalAward;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AwardServiceIntegrationTest {

    @Autowired
    private AwardService awardService;

    @Test
    void deveRetornarIntervalosDePremiacaoDosProdutores() {
        AwardIntervalsResponse response = awardService.getProducerAwardIntervals();

        List<IntervalAward> min = response.min();
        List<IntervalAward> max = response.max();

        assertThat(min).isNotNull();
        assertThat(max).isNotNull();
        assertThat(min).isNotEmpty();
        assertThat(max).isNotEmpty();

        Integer minInterval = min.get(0).interval();
        Integer maxInterval = max.get(0).interval();

        assertThat(min.stream().allMatch(i -> i.interval().equals(minInterval))).isTrue();
        assertThat(max.stream().allMatch(i -> i.interval().equals(maxInterval))).isTrue();
        assertThat(minInterval).isLessThanOrEqualTo(maxInterval);
    }
}