package com.railson.worstmovie.service;

import com.railson.worstmovie.dto.StudioDto;
import com.railson.worstmovie.dto.WinnerStudioDto;
import com.railson.worstmovie.dto.mapper.StudioMapper;
import com.railson.worstmovie.entity.Studio;
import com.railson.worstmovie.repository.StudioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudioService {

    @Autowired
    private StudioRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public Studio create(Studio studio){
        return repository.save(studio);
    }

    public Studio getStudioByName(String studioName){
        return repository.findByName(studioName).orElse(null);
    }

    public List<StudioDto> getAllStudiosDto(){
        return StudioMapper.toDto(repository.findAll(), modelMapper);
    }

    public List<WinnerStudioDto> getTop3WinnerStudios(){
        return repository.findTop3WinnerStudios().stream().limit(3).collect(Collectors.toList());
    }

}
