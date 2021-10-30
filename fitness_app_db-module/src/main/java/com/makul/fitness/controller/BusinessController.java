package com.makul.fitness.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.makul.fitness.dto.*;
import com.makul.fitness.model.ActiveProgram;
import com.makul.fitness.model.FitnessProgram;
import com.makul.fitness.model.Review;
import com.makul.fitness.service.api.BusinessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Api(tags = "Controller for performing some business tasks")
public class BusinessController {

    private final BusinessService businessService;
    private final ObjectMapper objectMapper;

    public BusinessController(BusinessService businessService, ObjectMapper objectMapper) {
        this.businessService = businessService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/user/{userId}/bookmark/{fitnessId}")
    @ApiOperation(value = "Bookmark the active program for the current user")
    public BookmarkDto addBookmark(@ApiParam(defaultValue = "1") @PathVariable("userId") long userId,
                                   @ApiParam(defaultValue = "2") @PathVariable("fitnessId") long fitnessProgramId){
     return objectMapper.convertValue(businessService.addBookmark(userId,fitnessProgramId), BookmarkDto.class);
    }

    @GetMapping("/user/{userId}/bookmarks")
    @ApiOperation(value = "Viewing the bookmarks of the current user")
    public List <BookmarkDto> showBookmarks(@ApiParam(defaultValue = "1") @PathVariable("userId") long userId){
        return businessService.viewBookmarks(userId)
                .stream()
                .map(bookmark -> objectMapper.convertValue(bookmark, BookmarkDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping ("/user/{userId}/program/active/{programId}")
    @ApiOperation(value = "Add an active program for the current user")
    public ActiveProgramDto addActiveProgram(@ApiParam(defaultValue = "3") @PathVariable("userId") long userId,
                                             @ApiParam(defaultValue = "3") @PathVariable("programId") long fitnessProgramId){
        return objectMapper.convertValue(businessService.addActiveProgram(userId,fitnessProgramId),
                ActiveProgramDto.class);
    }

    @PostMapping("/category/{categoryId}/program/fitness")
    @ApiOperation(value = "Add fitness program to user active programs")
    public FitnessProgramDto addFitnessProgram(@ApiParam(defaultValue = "4") @PathVariable long categoryId,
                                               @RequestBody FitnessProgramDto fitnessProgramDto){
        FitnessProgram fitnessProgram = objectMapper.convertValue(fitnessProgramDto, FitnessProgram.class);
        return objectMapper.convertValue(businessService.addFitnessProgram(categoryId,fitnessProgram),
                FitnessProgramDto.class);
    }

    @PutMapping("/program/fitness/schedule")
    @ApiOperation(value = "Create a schedule for the current user along with his wishes")
    public ActiveProgramDto createScheduleList(@RequestBody ActiveProgramDto activeProgramDto){
        ActiveProgram activeProgram = objectMapper.convertValue(activeProgramDto, ActiveProgram.class);
        return objectMapper.convertValue(businessService.createSchedule(activeProgram), ActiveProgramDto.class);
    }

    @PostMapping("/user/program/fitness/{fitnessProgramId}/review")
    @ApiOperation(value = "Add a review about fitness program")
    public ReviewDto addReview (@ApiParam(defaultValue = "6") @PathVariable long fitnessProgramId,
                               @RequestBody ReviewDto reviewDto){
        Review review = objectMapper.convertValue(reviewDto, Review.class);
        return objectMapper.convertValue(businessService.addReview(fitnessProgramId,review), ReviewDto.class);
    }

    @GetMapping("/schedule/exercise/{id}")
    @ApiOperation(value = "Mark exercise complited")
    public ExerciseScheduleDto updateSchedule(@ApiParam(defaultValue = "1") @PathVariable("id") long exerciseId){
       return objectMapper.convertValue(businessService.updateExercise(exerciseId),ExerciseScheduleDto.class);
    }
}
