package com.railson.worstmovie.dto.mapper;

import com.railson.worstmovie.dto.ProducerDto;
import com.railson.worstmovie.entity.Producer;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class ProducerMapper {

    public static ProducerDto toDto(Producer producer, ModelMapper mapper) {
        return mapper.map(producer, ProducerDto.class);
    }

    public static List<ProducerDto> toDto(List<Producer> studios, ModelMapper mapper) {
        return studios.stream().map(
                producer -> mapper.map(producer, ProducerDto.class)
        ).collect(Collectors.toList());
    }
}
