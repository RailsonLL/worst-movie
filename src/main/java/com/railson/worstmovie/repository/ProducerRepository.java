package com.railson.worstmovie.repository;

import com.railson.worstmovie.entity.Producer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProducerRepository extends JpaRepository<Producer, Integer> {

    Optional<Producer> findByName(String producerName);

    List<Producer> findByMoviesWinner(Boolean winner);

}
