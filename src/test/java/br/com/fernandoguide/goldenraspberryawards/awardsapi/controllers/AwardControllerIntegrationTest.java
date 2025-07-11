package br.com.fernandoguide.goldenraspberryawards.awardsapi.controllers;

import br.com.fernandoguide.goldenraspberryawards.awardsapi.dto.AwardIntervalsResponse;
import br.com.fernandoguide.goldenraspberryawards.awardsapi.fixture.IntervalAwardFixture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AwardControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    void deveRetornarExatamenteOsDadosDoCsvPadrao() {
        ResponseEntity<AwardIntervalsResponse> response = restTemplate.getForEntity(
            "/api/awards/producer-intervals", AwardIntervalsResponse.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        var body = response.getBody();
        assertThat(body).isNotNull();

        var esperadoMin = IntervalAwardFixture.buid("Joel Silver", 1, 1990, 1991);
        var esperadoMax = IntervalAwardFixture.buid("Matthew Vaughn", 13, 2002, 2015);

        assertThat(body.min()).containsExactly(esperadoMin);
        assertThat(body.max()).containsExactly(esperadoMax);
    }
}