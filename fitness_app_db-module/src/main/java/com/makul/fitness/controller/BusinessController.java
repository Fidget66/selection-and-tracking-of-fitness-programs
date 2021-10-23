package com.makul.fitness.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.makul.fitness.dto.*;
import com.makul.fitness.model.ActiveProgram;
import com.makul.fitness.model.FitnessProgram;
import com.makul.fitness.model.Review;
import com.makul.fitness.service.api.BusinessService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BusinessController {

    private final BusinessService businessService;
    private final ObjectMapper objectMapper;

    public BusinessController(BusinessService businessService, ObjectMapper objectMapper) {
        this.businessService = businessService;
        this.objectMapper = objectMapper;
    }

    @PostMapping("user/{userId}/bookmark/{fitnessId}")
    public void addBookmark(@PathVariable("userId") long userId,
                                   @PathVariable("fitnessId") long fitnessProgramId){
       businessService.addBookmark(userId,fitnessProgramId);
    }

    @GetMapping("user/{userId}/bookmarks")
    public List <BookmarkDto> showBookmarks(@PathVariable("userId") long userId){
        return businessService.viewBookmarks(userId)
                .stream()
                .map(bookmark -> objectMapper.convertValue(bookmark, BookmarkDto.class))
                .collect(Collectors.toList());
    }

    @PostMapping("user/{userId}/program/active/{programId}")
    public ActiveProgramDto addActiveProgram(@PathVariable("userId") long userId,
                                             @PathVariable("programId") long fitnessProgramId){
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

    @PostMapping("user/program/fitness/{fitnessProgramId}/review")
    public ReviewDto addReview (@PathVariable long fitnessProgramId,
                               @RequestBody ReviewDto reviewDto){
        Review review = objectMapper.convertValue(reviewDto, Review.class);
        return objectMapper.convertValue(businessService.addReview(fitnessProgramId,review), ReviewDto.class);
    }

    @PutMapping("/schedule/exercise/{id}")
    public ExerciseScheduleDto updateSchedule(@PathVariable("id") long exerciseId){
       return objectMapper.convertValue(businessService.updateExercise(exerciseId),ExerciseScheduleDto.class);
    }
}
