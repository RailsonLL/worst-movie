package com.railson.worstmovie.dto;

import com.railson.worstmovie.enums.Winner;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDto {
    private Integer id;
    private Integer year;
    private String title;
    private Set<SimpleStudioDto> studios;
    private Boolean winner;
}
