package com.railson.worstmovie.service;

import com.railson.worstmovie.dto.ProducerDto;
import com.railson.worstmovie.dto.mapper.ProducerMapper;
import com.railson.worstmovie.dto.mapper.StudioMapper;
import com.railson.worstmovie.entity.Producer;
import com.railson.worstmovie.enums.Winner;
import com.railson.worstmovie.repository.ProducerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProducerService {

    @Autowired
    private ProducerRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public Producer save(Producer producer){
        return repository.save(producer);
    }

    public Producer getProducerByName(String produerName){
        return repository.findByName(produerName).orElse(null);
    }

    /*public Studio getStudioById(Integer id){
        return repository.findById(id).orElse(null);
    }*/

    public List<ProducerDto> getAllProducersDto(){
        return ProducerMapper.toDto(repository.findAll(), modelMapper);
    }

    public List<ProducerDto> getProducersByMovieWinner(){
        return ProducerMapper.toDto(repository.findByMoviesWinner(true), modelMapper);
    }

}
