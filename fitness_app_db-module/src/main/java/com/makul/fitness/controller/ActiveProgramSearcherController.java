package com.makul.fitness.controller;

import com.makul.fitness.dto.ActiveProgramDto;
import com.makul.fitness.service.api.ActiveProgramSearcherService;
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
@Api(tags = "Controller for search User's active programs")
public class ActiveProgramSearcherController {

    private final ActiveProgramSearcherService activeSearcher;

    @GetMapping("/user/{userId}/programs/active")
    @ApiOperation(value = "Search User's complited active programs")
    public Page<ActiveProgramDto> readAllComplitedPrograms(@ApiParam(defaultValue = "1") @PathVariable UUID userId,
                                                           @ApiParam(defaultValue = "0") @RequestParam int page,
                                                           @ApiParam(defaultValue = "10") @RequestParam int size) {
        return CustomMapperUtil.convertToDto(activeSearcher.readComplitedPrograms(userId, page, size), ActiveProgramDto.class);
    }

    @GetMapping("/user/{userId}/program/active")
    @ApiOperation(value = "Read User's uncomplited active program")
    public ActiveProgramDto readUncomplitedProgram(@ApiParam(defaultValue = "1") @PathVariable UUID userId) {
        return CustomMapperUtil.convertToDto(activeSearcher.readUncomplitedProgram(userId), ActiveProgramDto.class);
    }
}
