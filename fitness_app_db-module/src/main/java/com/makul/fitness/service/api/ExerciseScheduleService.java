package com.makul.fitness.service.api;

import com.makul.fitness.model.ExerciseSchedule;

import java.time.LocalDate;
import java.util.List;

public interface ExerciseScheduleService {
    List<ExerciseSchedule> createAll(List<ExerciseSchedule> scheduleList);
    ExerciseSchedule update(long exerciseId);
    ExerciseSchedule read(long id);
    List<ExerciseSchedule> readExerciseByDate(LocalDate currentDate);
}
