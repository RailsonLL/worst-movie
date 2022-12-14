package com.railson.worstmovie.dto;

import com.railson.worstmovie.entity.Movie;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleMovieDto {
    private Integer id;
    private Integer year;
    private String title;
    private Boolean winner;

    public SimpleMovieDto(Movie movie){
        this.id = movie.getId();
        this.year = movie.getYear();
        this.title = movie.getTitle();
        this.winner = movie.getWinner();
    }
}
