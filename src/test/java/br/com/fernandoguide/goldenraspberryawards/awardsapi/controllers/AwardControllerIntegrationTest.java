package br.com.fernandoguide.goldenraspberryawards.awardsapi.controllers;

import br.com.fernandoguide.goldenraspberryawards.awardsapi.dto.AwardIntervalsResponse;
import br.com.fernandoguide.goldenraspberryawards.awardsapi.dto.IntervalAward;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AwardControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void deveRetornarIntervalosDePremiacaoDosProdutoresViaEndpoint() {
        ResponseEntity<AwardIntervalsResponse> response = restTemplate.getForEntity(
            "/api/awards/producer-intervals", AwardIntervalsResponse.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        AwardIntervalsResponse body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.min()).isNotEmpty();
        assertThat(body.max()).isNotEmpty();

        List<IntervalAward> min = body.min();
        List<IntervalAward> max = body.max();
        assertThat(min.get(0).interval()).isLessThanOrEqualTo(max.get(0).interval());

    }
}