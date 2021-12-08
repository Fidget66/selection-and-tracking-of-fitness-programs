package com.makul.fitness.controller;

import com.makul.fitness.dto.ActiveProgramDto;
import com.makul.fitness.dto.ExerciseScheduleDto;
import com.makul.fitness.model.ActiveProgram;
import com.makul.fitness.service.api.ClientBusinessService;
import com.makul.fitness.util.CustomMapperUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Api(tags = "Business Task Controller for Client")
public class ClientBusinessController {

    private final ClientBusinessService clientBusinessService;

    @PutMapping("/program/fitness/schedule")
    @ApiOperation(value = "Create a schedule for the current user along with his wishes")
    public ActiveProgramDto createScheduleList(@RequestBody ActiveProgramDto activeProgramDto) {
        ActiveProgram activeProgram = CustomMapperUtil.convertToDto(activeProgramDto, ActiveProgram.class);
        return CustomMapperUtil.convertToDto(clientBusinessService.createSchedule(activeProgram), ActiveProgramDto.class);
    }

    @GetMapping("/schedule/exercise/{id}")
    @ApiOperation(value = "Mark exercise complited")
    public ExerciseScheduleDto updateSchedule(@ApiParam(defaultValue = "1") @PathVariable("id") UUID exerciseId) {
        return CustomMapperUtil.convertToDto(clientBusinessService.updateExercise(exerciseId), ExerciseScheduleDto.class);
    }
}
