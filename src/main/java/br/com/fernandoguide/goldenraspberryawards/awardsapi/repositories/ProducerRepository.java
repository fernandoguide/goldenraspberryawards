package br.com.fernandoguide.goldenraspberryawards.awardsapi.repositories;

import br.com.fernandoguide.goldenraspberryawards.awardsapi.entity.Producer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProducerRepository extends JpaRepository<Producer, Long> {
    Optional<Producer> findByNameIgnoreCase(String name);
}
