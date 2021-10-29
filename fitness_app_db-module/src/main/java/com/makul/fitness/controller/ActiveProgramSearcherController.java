package com.makul.fitness.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.makul.fitness.dto.ActiveProgramDto;
import com.makul.fitness.service.api.ActiveProgramSearcherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Api(tags = "Controller for search User's active programs")
public class ActiveProgramSearcherController {

    private final ObjectMapper objectMapper;
    private final ActiveProgramSearcherService activeSearcher;

    public ActiveProgramSearcherController(ObjectMapper objectMapper, ActiveProgramSearcherService activeSearcher) {
        this.objectMapper = objectMapper;
        this.activeSearcher = activeSearcher;
    }

    @GetMapping("/user/{userId}/programs/active")
    @ApiOperation(value = "Search User's complited active programs")
    public List<ActiveProgramDto> readAllComplitedPrograms(@PathVariable long userId) {
        return activeSearcher.readComplitedPrograms(userId)
                .stream()
                .map(activeProgram -> objectMapper.convertValue(activeProgram, ActiveProgramDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/user/{userId}/program/active")
    @ApiOperation(value = "Read User's uncomplited active program")
    public ActiveProgramDto readUncomplitedProgram(@PathVariable long userId){
        return objectMapper.convertValue(activeSearcher.readUncomplitedProgram(userId), ActiveProgramDto.class);
    }

}
