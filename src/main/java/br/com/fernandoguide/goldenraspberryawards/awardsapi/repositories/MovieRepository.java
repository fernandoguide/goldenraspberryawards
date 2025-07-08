package br.com.fernandoguide.goldenraspberryawards.awardsapi.repositories;

import br.com.fernandoguide.goldenraspberryawards.awardsapi.entity.Movie;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    @EntityGraph(attributePaths = "producers")
    Optional<Movie> findByTitleIgnoreCase(String title);

    @EntityGraph(attributePaths = "producers")
    List<Movie> findByWinnerTrueOrderByYearAsc();
}
