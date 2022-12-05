package com.railson.worstmovie.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProducerDto {
    private Integer id;
    private String name;
    private Set<SimpleMovieDto> movies;
}
