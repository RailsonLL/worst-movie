package com.railson.worstmovie.controller;

import com.railson.worstmovie.dto.StudioDto;
import com.railson.worstmovie.dto.WinnerStudioDto;
import com.railson.worstmovie.service.StudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/studios")
public class StudioController {

    @Autowired
    private StudioService service;

    @GetMapping
    public ResponseEntity<List<StudioDto>> getAllStudios(){
        List<StudioDto> list = service.getAllStudiosDto();
        if (Objects.isNull(list) || list.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/studios-with-win-count")
    public ResponseEntity<List<WinnerStudioDto>> getTop3WinnerStudios(){
        List<WinnerStudioDto> list = service.getTop3WinnerStudios();
        if (Objects.isNull(list) || list.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(list);
    }

}
