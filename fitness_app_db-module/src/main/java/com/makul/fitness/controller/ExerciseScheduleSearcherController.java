package com.makul.fitness.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.makul.fitness.dto.ExerciseScheduleDto;
import com.makul.fitness.service.api.ExerciseScheduleSearcherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Api(tags = "Controller for read schedule list")
public class ExerciseScheduleSearcherController {

    private final ObjectMapper objectMapper;
    private final ExerciseScheduleSearcherService searcherService;

    public ExerciseScheduleSearcherController(ObjectMapper objectMapper, ExerciseScheduleSearcherService searcherService) {
        this.objectMapper = objectMapper;
        this.searcherService = searcherService;
    }

    @GetMapping("/program/active/{id}/exercises")
    @ApiOperation(value = "Get the schedule list of the active program")
    public List<ExerciseScheduleDto> getExercisesList(@ApiParam(defaultValue = "2")
                                                      @PathVariable("id") long activeProgramId) {
        return searcherService.readExerciseByActiveProgramId(activeProgramId)
                .stream()
                .map(exercise -> objectMapper.convertValue(exercise, ExerciseScheduleDto.class))
                .collect(Collectors.toList());
    }
}
