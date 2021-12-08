package com.makul.fitness.controller;

import com.makul.fitness.dto.ActiveProgramDto;
import com.makul.fitness.service.api.ActiveProgramService;
import com.makul.fitness.service.api.ClientBusinessService;
import com.makul.fitness.util.CustomMapperUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@Api(tags = "Controller for add/read active program by id")
@RequiredArgsConstructor
public class ActiveProgramController {

    private final ActiveProgramService programService;
    private final ClientBusinessService clientBusinessService;

    @GetMapping("/user/{userId}/program/active/{programId}")
    @ApiOperation(value = "Add an active program for the current user")
    public ActiveProgramDto addActiveProgram(@ApiParam(defaultValue = "3") @PathVariable("userId") UUID userId,
                                             @ApiParam(defaultValue = "3") @PathVariable("programId") UUID fitnessProgramId) {
        return CustomMapperUtil.convertToDto(clientBusinessService.addActiveProgram(userId, fitnessProgramId),
                ActiveProgramDto.class);
    }

    @GetMapping("/program/active/{id}")
    @ApiOperation(value = "Get a active program by id")
    public ActiveProgramDto readProgram(@ApiParam(defaultValue = "1") @PathVariable("id") UUID id) {
        return CustomMapperUtil.convertToDto(programService.read(id), ActiveProgramDto.class);
    }
}
