package com.makul.fitness.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.makul.fitness.dto.ActiveProgramDto;
import com.makul.fitness.dto.BookmarkDto;
import com.makul.fitness.dto.FitnessProgramDto;
import com.makul.fitness.dto.ReviewDto;
import com.makul.fitness.model.ActiveProgram;
import com.makul.fitness.model.FitnessProgram;
import com.makul.fitness.model.Review;
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

    @PostMapping("user/{userId}/bookmark/{fitnessId}")
    public BookmarkDto addBookmark(@PathVariable long userId, @PathVariable long fitnessProgramId){
        return objectMapper.convertValue(businessService.addBookmark(userId,fitnessProgramId), BookmarkDto.class);
    }

    @PostMapping("user/{userId}/program/active/")
    public ActiveProgramDto addActiveProgram(@PathVariable long userId, @PathVariable long fitnessProgramId){
        return objectMapper.convertValue(businessService.addActiveProgram(userId,fitnessProgramId),
                ActiveProgramDto.class);
    }

    @PostMapping("category/{categoryId}/program/fitness")
    public FitnessProgramDto addFitnessProgram(@PathVariable long categoryId,
                                               @RequestBody FitnessProgramDto fitnessProgramDto){
        FitnessProgram fitnessProgram = objectMapper.convertValue(fitnessProgramDto, FitnessProgram.class);
        return objectMapper.convertValue(businessService.addFitnessProgram(categoryId,fitnessProgram),
                FitnessProgramDto.class);
    }

    @PutMapping("admin/program/fitness/schedule")
    public ActiveProgramDto createScheduleList(@RequestBody ActiveProgramDto activeProgramDto){
        ActiveProgram activeProgram = objectMapper.convertValue(activeProgramDto, ActiveProgram.class);
        return objectMapper.convertValue(businessService.createSchedule(activeProgram), ActiveProgramDto.class);
    }

    @PostMapping("user/{userId}/program/fitness/{fitnessProgramId}/review")
    public ReviewDto addReview(@PathVariable long userId, @PathVariable long fitnessProgramId,
                               @RequestBody ReviewDto reviewDto){
        Review review = objectMapper.convertValue(reviewDto, Review.class);
        return objectMapper.convertValue(businessService.addReview(userId,fitnessProgramId,review), ReviewDto.class);
    }
}
