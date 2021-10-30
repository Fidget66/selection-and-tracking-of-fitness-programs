package com.makul.fitness.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.makul.fitness.dto.ActiveProgramDto;
import com.makul.fitness.service.api.ActiveProgramService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "Controller for read active program by id")
public class ActiveProgramController {

    private final ObjectMapper objectMapper;
    private final ActiveProgramService programService;

    public ActiveProgramController(ObjectMapper objectMapper, ActiveProgramService programService) {
        this.objectMapper = objectMapper;
        this.programService = programService;
    }

    @GetMapping("/program/active/{id}")
    @ApiOperation(value = "Get a active program by id")
    public ActiveProgramDto readProgram(@ApiParam(defaultValue = "1") @PathVariable ("id") long id){
        return objectMapper.convertValue(programService.read(id), ActiveProgramDto.class);
    }
}
