package com.railson.worstmovie.repository;

import com.railson.worstmovie.dto.SimpleMovieDto;
import com.railson.worstmovie.dto.YearWithMultipleWinnersDto;
import com.railson.worstmovie.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Integer> {

    List<Movie> findByWinnerAndYear(boolean winner, Integer year);

    @Query(
            value = "SELECT new com.railson.worstmovie.dto.SimpleMovieDto(m) "+
                    "FROM Movie m "+
                    "WHERE winner = :winner " +
                    "  AND COALESCE(:year, 0) IN (year, 0) " +
                    "ORDER BY id ASC "
    )
    Page<SimpleMovieDto> findByWinnerAndYear(Boolean winner, Integer year, Pageable pageable);

    @Query(
            value = "SELECT new com.railson.worstmovie.dto.SimpleMovieDto(m) "+
                    "FROM Movie m "+
                    "WHERE COALESCE(:year, 0) IN (year, 0) " +
                    "ORDER BY id ASC "
    )
    Page<SimpleMovieDto> findByYear(Integer year, Pageable pageable);

    @Query(
            value = "SELECT new com.railson.worstmovie.dto.YearWithMultipleWinnersDto(year AS year, COUNT(1) AS winnerCount) "+
                    "FROM Movie "+
                    "WHERE winner = true " +
                    "GROUP BY year "+
                    "HAVING COUNT(1) >= 2 "+
                    "ORDER BY year "
    )
    List<YearWithMultipleWinnersDto> findYearWithMultipleWinners();

    @Query(
            value = "SELECT DISTINCT year FROM Movie "+
                    "ORDER BY year ASC "
    )
    List<Integer> getYearsList();

    @Query(
            value = "SELECT DISTINCT year FROM Movie "+
                    "WHERE winner = true " +
                    "ORDER BY year ASC "
    )
    List<Integer> getWinnerYearsList();

}
