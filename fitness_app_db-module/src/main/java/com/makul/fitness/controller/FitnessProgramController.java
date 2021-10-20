package com.makul.fitness.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.makul.fitness.dto.FitnessProgramDto;
import com.makul.fitness.service.api.FitnessProgramService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FitnessProgramController {

    private final ObjectMapper objectMapper;
    private final FitnessProgramService fitnessProgramService;

    public FitnessProgramController(ObjectMapper objectMapper, FitnessProgramService fitnessProgramService) {
        this.objectMapper = objectMapper;
        this.fitnessProgramService = fitnessProgramService;
    }

    @GetMapping("/program/fitness/{id}")
    public FitnessProgramDto readFitnessProgram(@PathVariable("id")long id){
        return objectMapper.convertValue(fitnessProgramService.read(id), FitnessProgramDto.class);
    }
}
