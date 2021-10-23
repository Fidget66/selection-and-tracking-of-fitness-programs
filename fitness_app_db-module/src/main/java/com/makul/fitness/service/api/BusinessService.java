package com.makul.fitness.service.api;

import com.makul.fitness.model.*;

import java.util.List;

public interface BusinessService {
    void addBookmark(long userId, long fitnessProgramId);
    List <Bookmark> viewBookmarks(long userId);
    ActiveProgram addActiveProgram(long userId, long fitnessProgramId);
    FitnessProgram addFitnessProgram(long categoryId, FitnessProgram fitnessProgram);
    ActiveProgram createSchedule(ActiveProgram activeProgram);
    Review addReview(long fitnessProgramId, Review review);
    ExerciseSchedule updateExercise(long exerciseId);
}
