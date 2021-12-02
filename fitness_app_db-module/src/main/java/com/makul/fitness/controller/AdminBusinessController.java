package com.makul.fitness.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.makul.fitness.dto.FitnessProgramDto;
import com.makul.fitness.model.FitnessProgram;
import com.makul.fitness.service.api.AdminBusinessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "Business Task Controller for Administrator")
public class AdminBusinessController {

    private final ObjectMapper objectMapper;
    private final AdminBusinessService adminBusinessService;

    // ToDo ну ты понял
    public AdminBusinessController(ObjectMapper objectMapper, AdminBusinessService adminBusinessService) {
        this.objectMapper = objectMapper;
        this.adminBusinessService = adminBusinessService;
    }

    @PostMapping("/category/{categoryId}/program/fitness")
    @ApiOperation(value = "Add fitness program")
    public FitnessProgramDto addFitnessProgram(@ApiParam(defaultValue = "4") @PathVariable long categoryId,
                                               @RequestBody FitnessProgramDto fitnessProgramDto){
        FitnessProgram fitnessProgram = objectMapper.convertValue(fitnessProgramDto, FitnessProgram.class);
        return objectMapper.convertValue(adminBusinessService.addFitnessProgram(categoryId,fitnessProgram),
                FitnessProgramDto.class);
    }
}
