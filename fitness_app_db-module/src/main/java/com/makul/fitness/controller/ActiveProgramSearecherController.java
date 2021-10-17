package com.makul.fitness.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.makul.fitness.dto.ActiveProgramDto;
import com.makul.fitness.service.api.ActiveProgramSearcherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ActiveProgramSearecherController {

    private final ObjectMapper objectMapper;
    private final ActiveProgramSearcherService activeSearcher;

    public ActiveProgramSearecherController(ObjectMapper objectMapper, ActiveProgramSearcherService activeSearcher) {
        this.objectMapper = objectMapper;
        this.activeSearcher = activeSearcher;
    }

    @GetMapping("/user/{userId}/programs/active")
    public List<ActiveProgramDto> readAllComplitedPrograms(@PathVariable long userId) {
        return activeSearcher.readComplitedPrograms(userId)
                .stream()
                .map(activeProgram -> objectMapper.convertValue(activeProgram, ActiveProgramDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/user/{userId}/program/active")
    public ActiveProgramDto readUncomplitedProgram(@PathVariable long userId){
        return objectMapper.convertValue(activeSearcher.readUncomplitedProgram(userId), ActiveProgramDto.class);
    }

}
