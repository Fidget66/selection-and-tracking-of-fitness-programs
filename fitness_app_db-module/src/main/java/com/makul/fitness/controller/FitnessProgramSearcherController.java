package com.makul.fitness.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.makul.fitness.dto.FitnessProgramDto;
import com.makul.fitness.service.api.FitnessProgramsSearcherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class FitnessProgramSearcherController {

    private final ObjectMapper objectMapper;
    private final FitnessProgramsSearcherService fitnessSearcherService;

    public FitnessProgramSearcherController(ObjectMapper objectMapper,
                                            FitnessProgramsSearcherService fitnessSearcherService) {
        this.objectMapper = objectMapper;
        this.fitnessSearcherService = fitnessSearcherService;
    }

    @GetMapping("/category/{id}/program/fitness")
    public List<FitnessProgramDto> readFitnessProgramList(@PathVariable long categoryId){
        return fitnessSearcherService.readFitnessProgram(categoryId)
                .stream()
                .map(fitnessProgram -> objectMapper.convertValue(fitnessProgram,FitnessProgramDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("user/(userId)/category/{id}/program/fitness/{duration}")
    public List<FitnessProgramDto> readFitnessProgramListWithRestrictions (@PathVariable long userId,
                                                                           @PathVariable long categoryId,
                                                                           @PathVariable int durationLimit){
        return fitnessSearcherService.readFitnessProgramWithRestrictions(userId, categoryId, durationLimit)
                .stream()
                .map(fitnessProgram -> objectMapper.convertValue(fitnessProgram,FitnessProgramDto.class))
                .collect(Collectors.toList());
    }
}
