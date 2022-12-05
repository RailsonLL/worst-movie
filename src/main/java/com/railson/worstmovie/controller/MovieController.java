package com.railson.worstmovie.controller;

import com.railson.worstmovie.dto.MovieDto;
import com.railson.worstmovie.dto.ProducerWinIntervalMinMaxDto;
import com.railson.worstmovie.dto.SimpleMovieDto;
import com.railson.worstmovie.dto.YearWithMultipleWinnersDto;
import com.railson.worstmovie.entity.Movie;
import com.railson.worstmovie.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/movie")
public class MovieController {

    @Autowired
    private MovieService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<SimpleMovieDto> getAllMovies(){
        return service.getAllMovies();
    }

    /*@GetMapping("/studio")
    @ResponseStatus(HttpStatus.OK)
    public List<StudioDto> getAllStudios(){
        return service.getAllStudiosDto();
    }*/

    /*@GetMapping("/studio/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StudioDto getStudioById(@PathVariable Integer id){
        return service.getStudioById(id);
    }*/

    /*@GetMapping("/producer")
    @ResponseStatus(HttpStatus.OK)
    public List<ProducerDto> getAllProducers(){
        return service.getAllProducers();
    }*/

    @GetMapping("/max-min-win-interval-for-producers")
    @ResponseStatus(HttpStatus.OK)
    public ProducerWinIntervalMinMaxDto getProducerWinIntervalMinMax(){
        return service.getProducerWinIntervalMinMax();
    }

    @GetMapping("/years-with-multiple-winners")
    @ResponseStatus(HttpStatus.OK)
    public List<YearWithMultipleWinnersDto> yearsWithMultipleWinners(){
        return service.getYearsWithMultipleWinners();
    }

    @GetMapping("/years-list")
    @ResponseStatus(HttpStatus.OK)
    public List<Integer> getYearsList(){
        return service.getYearsList();
    }

    @GetMapping("/winner-years-list")
    @ResponseStatus(HttpStatus.OK)
    public List<Integer> getWinnerYearsList(){
        return service.getWinnerYearsList();
    }

    @GetMapping("/movies-by-winner-and-year")
    @ResponseStatus(HttpStatus.OK)
    public List<SimpleMovieDto> getMoviesByWinnerAndYear(@RequestParam boolean winner, @RequestParam Integer year){
        return service.getMoviesByWinnerAndYear(winner, year);
    }

    @GetMapping("/movies-page-by-winner-year")
    @ResponseStatus(HttpStatus.OK)
    public Page<SimpleMovieDto> getMoviesPageByWinnerAndYear(
            @RequestParam(required = false) String winner,
            @RequestParam(required = false) String year,
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
