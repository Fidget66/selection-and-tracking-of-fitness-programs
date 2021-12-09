package com.makul.fitness.controller;

import com.makul.fitness.dto.ExerciseScheduleDto;
import com.makul.fitness.service.api.ExerciseScheduleSearcherService;
import com.makul.fitness.util.CustomMapperUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Api(tags = "Controller for read schedule list")
public class ExerciseScheduleSearcherController {

    private final ExerciseScheduleSearcherService searcherService;

    @GetMapping("/program/active/{id}/exercises")
    @ApiOperation(value = "Get the schedule list of the active program")
    public Page<ExerciseScheduleDto> getExercisesList(@ApiParam(defaultValue = "2") @PathVariable("id") UUID activeProgramId,
                                                      @ApiParam(defaultValue = "0") @RequestParam int page,
                                                      @ApiParam(defaultValue = "10") @RequestParam int size) {
        return CustomMapperUtil.convertToDto(searcherService.readExerciseByActiveProgramId(activeProgramId, page, size),
                ExerciseScheduleDto.class);
    }
}
