package com.railson.worstmovie.dto.mapper;

import com.railson.worstmovie.dto.StudioDto;
import com.railson.worstmovie.entity.Studio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class StudioMapper {

    public static StudioDto toDto(Studio studio, ModelMapper mapper) {
        return mapper.map(studio, StudioDto.class);
    }

    public static List<StudioDto> toDto(List<Studio> studios, ModelMapper mapper) {
        return studios.stream().map(
                studio -> mapper.map(studio, StudioDto.class)
        ).collect(Collectors.toList());
    }
}
