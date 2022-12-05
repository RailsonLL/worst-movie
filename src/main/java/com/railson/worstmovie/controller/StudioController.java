package com.railson.worstmovie.controller;

import com.railson.worstmovie.dto.StudioDto;
import com.railson.worstmovie.dto.WinnerStudioDto;
import com.railson.worstmovie.service.StudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/studio")
public class StudioController {

    @Autowired
    private StudioService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<StudioDto> getAllStudios(){
        return service.getAllStudiosDto();
    }

    @GetMapping("/studios-with-win-count")
    @ResponseStatus(HttpStatus.OK)
    public List<WinnerStudioDto> getTop3WinnerStudios(){
        return service.getTop3WinnerStudios();
    }

}
