package com.makul.fitness.service.api;

import com.makul.fitness.model.*;

public interface BusinessService {
    Bookmark addBookmark(long userId, long fitnessProgramId);
    ActiveProgram addActiveProgram(long userId, long fitnessProgramId);
    FitnessProgram addFitnessProgram(long categoryId, FitnessProgram fitnessProgram);
    ActiveProgram createSchedule(ActiveProgram activeProgram);
    Review addReview(long userId, long fitnessProgramId, Review review);
}
