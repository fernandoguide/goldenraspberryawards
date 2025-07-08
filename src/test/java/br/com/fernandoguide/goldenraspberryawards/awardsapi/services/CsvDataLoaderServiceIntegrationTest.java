package br.com.fernandoguide.goldenraspberryawards.awardsapi.services;

import br.com.fernandoguide.goldenraspberryawards.awardsapi.entity.Movie;
import br.com.fernandoguide.goldenraspberryawards.awardsapi.entity.Producer;
import br.com.fernandoguide.goldenraspberryawards.awardsapi.repositories.MovieRepository;
import br.com.fernandoguide.goldenraspberryawards.awardsapi.repositories.ProducerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CsvDataLoaderServiceIntegrationTest {


    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ProducerRepository producerRepository;

    @Test
    void deveCarregarFilmesEDadosDosProdutoresDoCsv() {
        Optional<Movie> movie = movieRepository.findByTitleIgnoreCase("Can't Stop the Music");
        assertThat(movie).isPresent();
        assertThat(movie.get().getYear()).isEqualTo(1980);

        Optional<Producer> producer = producerRepository.findByNameIgnoreCase("Allan Carr");
        assertThat(producer).isPresent();

        assertThat(movie.get().getProducers()).extracting(Producer::getName)
            .contains("Allan Carr");

        List<Movie> winners = movieRepository.findByWinnerTrueOrderByYearAsc();
        assertThat(winners).isNotEmpty();

        assertThat(movieRepository.count()).isGreaterThan(0);
        assertThat(producerRepository.count()).isGreaterThan(0);
    }

}