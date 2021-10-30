package com.makul.fitness.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.makul.fitness.dto.FitnessProgramDto;
import com.makul.fitness.service.api.FitnessProgramService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "Controller for read fitness program by id")
public class FitnessProgramController {

    private final ObjectMapper objectMapper;
    private final FitnessProgramService fitnessProgramService;

    public FitnessProgramController(ObjectMapper objectMapper, FitnessProgramService fitnessProgramService) {
        this.objectMapper = objectMapper;
        this.fitnessProgramService = fitnessProgramService;
    }

    @GetMapping("/program/fitness/{id}")
    @ApiOperation(value = "Get a fitness program by id")
    public FitnessProgramDto readFitnessProgram(@ApiParam(defaultValue = "6")@PathVariable("id")long id){
        return objectMapper.convertValue(fitnessProgramService.read(id), FitnessProgramDto.class);
    }
}
