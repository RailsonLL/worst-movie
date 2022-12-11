package com.railson.worstmovie.service;

import com.railson.worstmovie.dto.ProducerDto;
import com.railson.worstmovie.dto.ProducerWinIntervalMinMaxDto;
import com.railson.worstmovie.dto.SimpleMovieDto;
import com.railson.worstmovie.dto.YearsWithMultipleWinnersDto;
import com.railson.worstmovie.dto.mapper.MovieMapper;
import com.railson.worstmovie.entity.Movie;
import com.railson.worstmovie.repository.MovieRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class MovieService {

    @Autowired
    private MovieRepository repository;

    @Autowired
    private ProducerService producerService;

    @Autowired
    private ModelMapper modelMapper;

    public Movie create(Movie movie){
        return repository.save(movie);
    }

    public List<SimpleMovieDto> getAllMovies(){
        return MovieMapper.toDto(repository.findAll(), modelMapper);
    }


    public List<YearsWithMultipleWinnersDto> getYearsWithMultipleWinners() {
        return  repository.findYearWithMultipleWinners();
    }

    public List<Integer> getYearsList() {
        return repository.getYearsList();
    }

    public List<Integer> getWinnerYearsList() {
        return repository.getWinnerYearsList();
    }

    public List<SimpleMovieDto> getMoviesByWinnerAndYear(boolean winner, Integer year) {
        return MovieMapper.toDto(repository.findByWinnerAndYear(winner, year), modelMapper);
    }

    public Page<SimpleMovieDto> getMoviesPageByWinnerAndYear(Boolean winner, Integer year, Integer page, Integer size) {
        Page<SimpleMovieDto> moviePage;
        //Poderia utilizar também o criteria query ou specification nesse caso
        if (Objects.nonNull(winner)) {
            moviePage = repository.findByWinnerAndYear(winner, year, PageRequest.of(page, size));
        } else moviePage = repository.findByYear(year, PageRequest.of(page, size));

        return moviePage;
    }

    public ProducerWinIntervalMinMaxDto getProducerWinIntervalMinMax(){
        ProducerWinIntervalMinMaxDto intervalMinMax = new ProducerWinIntervalMinMaxDto();
        Comparator<SimpleMovieDto> compare = Comparator.comparing(SimpleMovieDto::getYear);

        int intervalMin = 999;
        int intervalMax = 0;
        List<ProducerDto> producerList = producerService.getProducersByMovieWinner();
        for (ProducerDto producer : producerList) {
            List<SimpleMovieDto> movieWinnerList = producer.getMovies().stream().filter(
                    movie -> movie.getWinner().equals(true)).sorted(compare).collect(Collectors.toList());

            if (movieWinnerList.size() >= 2) {
                int previousWin = movieWinnerList.get(0).getYear();
                for (int i=1; i < movieWinnerList.size(); i++) {
                    int followingWin = movieWinnerList.get(i).getYear();
                    int interval = followingWin - previousWin;

                    if (interval <= intervalMin) {
                        intervalMinMax.addIntervalMin(producer.getName(), interval, previousWin, followingWin);
                        intervalMin = interval;
                    }
                    if (interval >= intervalMax) {
                        intervalMinMax.addIntervalMax(producer.getName(), interval, previousWin, followingWin);
                        intervalMax = interval;
                    }
                    previousWin = followingWin;
                }
            }
        }

        return intervalMinMax;
    }

}
