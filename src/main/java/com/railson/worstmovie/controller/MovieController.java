package com.railson.worstmovie.controller;

import com.railson.worstmovie.dto.ProducerWinIntervalMinMaxDto;
import com.railson.worstmovie.dto.SimpleMovieDto;
import com.railson.worstmovie.dto.YearsWithMultipleWinnersDto;
import com.railson.worstmovie.entity.Movie;
import com.railson.worstmovie.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<SimpleMovieDto>> getAllMovies(){
        List<SimpleMovieDto> list = service.getAllMovies();
        if (Objects.isNull(list) || list.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/max-min-win-interval-for-producers")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProducerWinIntervalMinMaxDto> getProducerWinIntervalMinMax(){
        ProducerWinIntervalMinMaxDto dto = service.getProducerWinIntervalMinMax();
        if (Objects.isNull(dto)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/years-with-multiple-winners")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<YearsWithMultipleWinnersDto>> yearsWithMultipleWinners(){
        List<YearsWithMultipleWinnersDto> list = service.getYearsWithMultipleWinners();
        if (Objects.isNull(list) || list.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/years-list")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Integer>> getYearsList(){
        List<Integer> list = service.getYearsList();
        if (Objects.isNull(list) || list.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/winner-years-list")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Integer>> getWinnerYearsList(){
        List<Integer> list = service.getWinnerYearsList();
        if (Objects.isNull(list) || list.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/movies-by-winner-and-year")
    @ResponseStatus(HttpStatus.OK)
    public List<SimpleMovieDto> getMoviesByWinnerAndYear(@RequestParam boolean winner, @RequestParam Integer year){
        return service.getMoviesByWinnerAndYear(winner, year);
    }

    @GetMapping("/movies-page-by-winner-year")
    @ResponseStatus(HttpStatus.OK)
    public Page<SimpleMovieDto> getMoviesPageByWinnerAndYear(
            @RequestParam String winner,
            @RequestParam String year,
            @RequestParam Integer page,
            @RequestParam Integer size
    ){
        Boolean winnerAux = winner.equals("null") ? null : Boolean.valueOf(winner);
        Integer yearAux = year.equals("null") ? null : Integer.valueOf(year);
        return service.getMoviesPageByWinnerAndYear(winnerAux, yearAux, page, size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Movie create(@RequestBody Movie movie){
        return service.create(movie);
    }

}
