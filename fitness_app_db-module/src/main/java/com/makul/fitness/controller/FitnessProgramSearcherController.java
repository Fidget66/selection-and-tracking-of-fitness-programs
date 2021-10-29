package com.makul.fitness.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.makul.fitness.dto.FitnessProgramDto;
import com.makul.fitness.service.api.FitnessProgramsSearcherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Api(tags = "Controller for search fitness programs")
public class FitnessProgramSearcherController {

    private final ObjectMapper objectMapper;
    private final FitnessProgramsSearcherService fitnessSearcherService;

    public FitnessProgramSearcherController(ObjectMapper objectMapper,
                                            FitnessProgramsSearcherService fitnessSearcherService) {
        this.objectMapper = objectMapper;
        this.fitnessSearcherService = fitnessSearcherService;
    }

    @GetMapping("/category/{id}/program/fitness")
    @ApiOperation(value = "Get fitness programs of current category ")
    public List<FitnessProgramDto> readFitnessProgramList(@PathVariable("id") long categoryId){
        return fitnessSearcherService.readFitnessProgram(categoryId)
                .stream()
                .map(fitnessProgram -> objectMapper.convertValue(fitnessProgram,FitnessProgramDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/user/{userId}/program/fitness/{duration}")
    @ApiOperation(value = "Get restricted fitness programs")
    public List<FitnessProgramDto> readFitnessProgramListWithRestrictions (@PathVariable("userId") long userId,
                                                                           @PathVariable("duration") int durationLimit){
        return fitnessSearcherService.readFitnessProgramWithRestrictions(userId, durationLimit)
                .stream()
                .map(fitnessProgram -> objectMapper.convertValue(fitnessProgram,FitnessProgramDto.class))
                .collect(Collectors.toList());
    }
}
