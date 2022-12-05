package com.railson.worstmovie.dto.mapper;

import com.railson.worstmovie.dto.SimpleMovieDto;
import com.railson.worstmovie.entity.Movie;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class MovieMapper {

    public static SimpleMovieDto toDto(Movie movie, ModelMapper mapper) {
        return mapper.map(movie, SimpleMovieDto.class);
    }

    public static List<SimpleMovieDto> toDto(List<Movie> movies, ModelMapper mapper) {
        return movies.stream().map(
                movie -> mapper.map(movie, SimpleMovieDto.class)
        ).collect(Collectors.toList());
    }

}
