package br.com.fernandoguide.goldenraspberryawards.awardsapi.controllers;

import br.com.fernandoguide.goldenraspberryawards.awardsapi.dto.AwardIntervalsResponse;
import br.com.fernandoguide.goldenraspberryawards.awardsapi.fixture.IntervalAwardFixture;
import org.junit.jupiter.api.DisplayName;
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

    public static final String PRODUCER_NAME_MIN = "Joel Silver";
    public static final int INTERVAL_MIN = 1;
    public static final int PREVIOUS_WIN_MIN = 1990;
    public static final int FOLLOWING_WIN_MIN = 1991;

    public static final String PRODUCER_NAME_MAX = "Matthew Vaughn";
    public static final int INTERVAL_MAX = 13;
    public static final int PREVIOUS_WIN_MAX = 2002;
    public static final int FOLLOWING_WIN_MAX = 2015;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("Deve retornar exatamente os dados do CSV padrão")
    void deveRetornarExatamenteOsDadosDoCsvPadrao() {
        ResponseEntity<AwardIntervalsResponse> response = restTemplate.getForEntity(
            "/api/awards/producer-intervals", AwardIntervalsResponse.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        var body = response.getBody();
        assertThat(body).isNotNull();

        var esperadoMin = IntervalAwardFixture.buid(PRODUCER_NAME_MIN, INTERVAL_MIN, PREVIOUS_WIN_MIN, FOLLOWING_WIN_MIN);
        var esperadoMax = IntervalAwardFixture.buid(PRODUCER_NAME_MAX, INTERVAL_MAX, PREVIOUS_WIN_MAX, FOLLOWING_WIN_MAX);

        assertThat(body.min()).containsExactly(esperadoMin);
        assertThat(body.max()).containsExactly(esperadoMax);
    }
}