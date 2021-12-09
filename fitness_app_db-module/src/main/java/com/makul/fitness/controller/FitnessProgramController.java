package com.makul.fitness.controller;

import com.makul.fitness.dto.FitnessProgramDto;
import com.makul.fitness.model.FitnessProgram;
import com.makul.fitness.service.api.AdminBusinessService;
import com.makul.fitness.service.api.FitnessProgramService;
import com.makul.fitness.util.CustomMapperUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Api(tags = "Controller for add/read fitness program by id")
public class FitnessProgramController {

    private final FitnessProgramService fitnessProgramService;
    private final AdminBusinessService adminBusinessService;

    @PostMapping("/category/{categoryId}/program/fitness")
    @ApiOperation(value = "Add fitness program")
    public FitnessProgramDto addFitnessProgram(@ApiParam(defaultValue = "4") @PathVariable UUID categoryId,
                                               @RequestBody FitnessProgramDto fitnessProgramDto){
        FitnessProgram fitnessProgram = CustomMapperUtil.convertToDto(fitnessProgramDto, FitnessProgram.class);
        return CustomMapperUtil.convertToDto(adminBusinessService.addFitnessProgram(categoryId,fitnessProgram),
                FitnessProgramDto.class);
    }

    @GetMapping("/program/fitness/{id}")
    @ApiOperation(value = "Get a fitness program by id")
    public FitnessProgramDto readFitnessProgram(@ApiParam(defaultValue = "6")@PathVariable("id") UUID id){
        return CustomMapperUtil.convertToDto(fitnessProgramService.read(id), FitnessProgramDto.class);
    }
}
