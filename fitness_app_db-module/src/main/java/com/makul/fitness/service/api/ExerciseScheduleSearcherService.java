package com.makul.fitness.service.api;

import com.makul.fitness.model.ExerciseSchedule;

import java.time.LocalDate;
import java.util.List;

public interface ExerciseScheduleSearcherService {
    List<ExerciseSchedule> readExerciseByDate(LocalDate currentDate);
    List<ExerciseSchedule> readExerciseByActiveProgramId(long activeProgramId);
}
