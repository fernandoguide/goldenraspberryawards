package br.com.fernandoguide.goldenraspberryawards.awardsapi.services;

import br.com.fernandoguide.goldenraspberryawards.awardsapi.dto.AwardIntervalsResponse;
import br.com.fernandoguide.goldenraspberryawards.awardsapi.dto.IntervalAward;
import br.com.fernandoguide.goldenraspberryawards.awardsapi.entity.Movie;
import br.com.fernandoguide.goldenraspberryawards.awardsapi.entity.Producer;
import br.com.fernandoguide.goldenraspberryawards.awardsapi.repositories.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AwardService {

    private final MovieRepository movieRepository;

    public AwardService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public AwardIntervalsResponse getProducerAwardIntervals() {
        var winnerMovies = movieRepository.findByWinnerTrueOrderByYearAsc();

        Map<String, List<Integer>> producerWins = new HashMap<>();

        for (Movie movie : winnerMovies) {
            for (Producer producer : movie.getProducers()) {
                producerWins.computeIfAbsent(producer.getName(), k -> new ArrayList<>()).add(movie.getYear());
            }
        }

        List<IntervalAward> allIntervals = new ArrayList<>();

        for (Map.Entry<String, List<Integer>> entry : producerWins.entrySet()) {
            String producerName = entry.getKey();
            List<Integer> years = entry.getValue();
            Collections.sort(years);

            if (years.size() >= 2) {
                for (int i = 0; i < years.size() - 1; i++) {
                    Integer previousWin = years.get(i);
                    Integer followingWin = years.get(i + 1);
                    Integer interval = followingWin - previousWin;
                    allIntervals.add(new IntervalAward(producerName, interval, previousWin, followingWin));
                }
            }
        }

        if (allIntervals.isEmpty()) {
            return new AwardIntervalsResponse(Collections.emptyList(), Collections.emptyList());
        }

        Optional<Integer> minInterval = allIntervals.stream()
            .map(IntervalAward::interval)
            .min(Comparator.naturalOrder());

        Optional<Integer> maxInterval = allIntervals.stream()
            .map(IntervalAward::interval)
            .max(Comparator.naturalOrder());

        List<IntervalAward> minAwards = new ArrayList<>();
        List<IntervalAward> maxAwards = new ArrayList<>();

        minInterval.ifPresent(minVal ->
            minAwards.addAll(allIntervals.stream()
                .filter(award -> award.interval().equals(minVal))
                .toList())
        );

        maxInterval.ifPresent(maxVal ->
            maxAwards.addAll(allIntervals.stream()
                .filter(award -> award.interval().equals(maxVal))
                .toList())
        );

        return new AwardIntervalsResponse(minAwards, maxAwards);
    }
}
