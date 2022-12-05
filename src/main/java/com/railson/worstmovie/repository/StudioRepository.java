package com.railson.worstmovie.repository;

import com.railson.worstmovie.dto.WinnerStudioDto;
import com.railson.worstmovie.entity.Studio;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StudioRepository extends JpaRepository<Studio, Integer> {

    Optional<Studio> findByName(String studioName);

    @Query(
            value = "SELECT new com.railson.worstmovie.dto.WinnerStudioDto(s.name AS name, COUNT(1) AS winCount) "+
                    "FROM Studio s "+
                    "JOIN s.movies m "+
                    "WHERE m.winner = true "+
                    "GROUP BY name " +
                    "ORDER BY winCount DESC "
    )
    List<WinnerStudioDto> findTop3WinnerStudios();

}
