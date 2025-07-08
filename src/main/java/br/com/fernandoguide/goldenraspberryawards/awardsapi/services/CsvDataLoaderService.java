package br.com.fernandoguide.goldenraspberryawards.awardsapi.services;

import br.com.fernandoguide.goldenraspberryawards.awardsapi.entity.Movie;
import br.com.fernandoguide.goldenraspberryawards.awardsapi.entity.Producer;
import br.com.fernandoguide.goldenraspberryawards.awardsapi.repositories.MovieRepository;
import br.com.fernandoguide.goldenraspberryawards.awardsapi.repositories.ProducerRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class CsvDataLoaderService {

    private static final Logger logger = LoggerFactory.getLogger(CsvDataLoaderService.class);

    private final MovieRepository movieRepository;
    private final ProducerRepository producerRepository;

    @Value("classpath:movielist.csv")
    private Resource csvFile;

    public CsvDataLoaderService(MovieRepository movieRepository, ProducerRepository producerRepository) {
        this.movieRepository = movieRepository;
        this.producerRepository = producerRepository;
    }

    @PostConstruct
    @Transactional
    public void loadCsvData() {
        if (movieRepository.count() > 0) {
            logger.info("Database already populated. Skipping CSV loading.");
            return;
        }

        logger.info("Starting to load data from CSV: {}", csvFile.getFilename());
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(csvFile.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                parseAndSaveMovie(line);
            }
            logger.info("Finished loading data from CSV. Total movies: {}", movieRepository.count());
        } catch (Exception e) {
            logger.error("Failed to load CSV data: {}", e.getMessage(), e);
        }
    }

    private void parseAndSaveMovie(String line) {
        String[] parts = line.split(";", -1);

        if (parts.length < 5) {
            logger.warn("Skipping malformed line: {}", line);
            return;
        }

        try {
            Integer year = Integer.parseInt(parts[0].trim());
            String title = parts[1].trim();
            String studios = parts[2].trim();
            String producersStr = parts[3].trim();
            Boolean winner = parts[4].trim().equalsIgnoreCase("yes");

            Movie movie = new Movie(year, title, studios, winner);

            Set<Producer> producers = parseProducers(producersStr);
            producers.forEach(movie::addProducer);

            movieRepository.save(movie);
        } catch (NumberFormatException e) {
            logger.error("Error parsing year in line: {}. Skipping.", line);
        } catch (Exception e) {
            logger.error("Error processing line: {}. Error: {}", line, e.getMessage(), e);
        }
    }

    private Set<Producer> parseProducers(String producersStr) {
        Set<Producer> uniqueProducers = new HashSet<>();
        String[] andSplit = producersStr.split("\\s+and\\s+");
        for (String part : andSplit) {
            String[] commaSplit = part.split("\\s*,\\s*");
            for (String name : commaSplit) {
                String trimmedName = name.trim();
                if (!trimmedName.isEmpty()) {
                    uniqueProducers.add(getOrCreateProducer(trimmedName));
                }
            }
        }
        return uniqueProducers;
    }

    private Producer getOrCreateProducer(String name) {
        Optional<Producer> existingProducer = producerRepository.findByNameIgnoreCase(name);
        return existingProducer.orElseGet(() -> {
            Producer newProducer = new Producer(name);
            return producerRepository.save(newProducer);
        });
    }
}
