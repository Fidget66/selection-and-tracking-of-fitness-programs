package com.makul.fitness.service.api;

import com.makul.fitness.model.Bookmark;
import com.makul.fitness.model.ExerciseSchedule;
import com.makul.fitness.model.FitnessProgram;
import com.makul.fitness.model.Users;
import java.util.Set;

public interface BusinessService {
    Users addBookmark(long userId, FitnessProgram fitnessProgram);
    Bookmark completionMark (long bookmarkId, Set<ExerciseSchedule> scheduleList);
    void addActiveProgram(long userId, FitnessProgram fitnessProgram);
    void addFitnessProgram(long categoryId, FitnessProgram fitnessProgram);
}
