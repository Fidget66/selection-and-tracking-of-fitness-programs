package com.makul.fitness.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.makul.fitness.dto.BookmarkDto;
import com.makul.fitness.dto.FitnessProgramDto;
import com.makul.fitness.model.Bookmark;
import com.makul.fitness.model.FitnessProgram;
import com.makul.fitness.model.Users;
import com.makul.fitness.service.api.BusinessService;
import org.springframework.web.bind.annotation.*;

@RestController
public class BusinessController {

    private final BusinessService businessService;
    private final ObjectMapper objectMapper;

    public BusinessController(BusinessService businessService, ObjectMapper objectMapper) {
        this.businessService = businessService;
        this.objectMapper = objectMapper;
    }

    @PostMapping("user/bookmark/{userId}")
    public Users addBookmark(@PathVariable long userId, @RequestBody FitnessProgramDto fitnessProgramDto){
        FitnessProgram fitnessProgram = objectMapper.convertValue(fitnessProgramDto, FitnessProgram.class);
        return objectMapper.convertValue(businessService.addBookmark(userId,fitnessProgram),Users.class);
    }

    @PostMapping("user/program/active/{userId}")
    public void addActiveProgramm(@PathVariable long userId, @RequestBody FitnessProgramDto fitnessProgramDto){
        FitnessProgram fitnessProgram = objectMapper.convertValue(fitnessProgramDto, FitnessProgram.class);
        businessService.addActiveProgram(userId,fitnessProgram);
    }

    @PostMapping("admin/program/fitness/{categoryId}")
    public void addFitnessProgram(@PathVariable long categoryId, @RequestBody FitnessProgramDto fitnessProgramDto){
        FitnessProgram fitnessProgram = objectMapper.convertValue(fitnessProgramDto, FitnessProgram.class);
        businessService.addFitnessProgram(categoryId,fitnessProgram);
    }
}
