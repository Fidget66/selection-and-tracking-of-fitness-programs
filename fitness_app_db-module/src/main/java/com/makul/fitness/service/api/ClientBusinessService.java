package com.makul.fitness.service.api;

import com.makul.fitness.model.*;

import java.util.List;

public interface ClientBusinessService {
    Bookmark addBookmark(long userId, long fitnessProgramId);
    List <Bookmark> viewBookmarks(long userId);
    ActiveProgram addActiveProgram(long userId, long fitnessProgramId);
    ActiveProgram createSchedule(ActiveProgram activeProgram);
    Review addReview(long fitnessProgramId, Review review);
    ExerciseSchedule updateExercise(long exerciseId);
    ActiveProgram update(ActiveProgram inputActiveProgram);
}
