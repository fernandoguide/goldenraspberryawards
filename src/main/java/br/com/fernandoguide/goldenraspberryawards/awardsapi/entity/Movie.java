package br.com.fernandoguide.goldenraspberryawards.awardsapi.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "movies")
@Getter
@Setter
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "\"year\"")
    private Integer year;

    @Column(name = "title")
    private String title;

    @Column(name = "studios")
    private String studios;

    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(
        name = "movie_producer",
        joinColumns = @JoinColumn(name = "movie_id"),
        inverseJoinColumns = @JoinColumn(name = "producer_id")
    )
    private Set<Producer> producers = new HashSet<>();

    private Boolean winner;

    public Movie() {
    }

    public Movie(Integer year, String title, String studios, Boolean winner) {
        this.year = year;
        this.title = title;
        this.studios = studios;
        this.winner = winner;
    }

    public void addProducer(Producer producer) {
        this.producers.add(producer);
    }
}