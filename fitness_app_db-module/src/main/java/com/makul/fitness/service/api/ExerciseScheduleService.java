package com.makul.fitness.service.api;

import com.makul.fitness.model.ExerciseSchedule;

import java.util.List;
import java.util.UUID;

public interface ExerciseScheduleService {
    List<ExerciseSchedule> createAll(List<ExerciseSchedule> scheduleList);
    ExerciseSchedule update(UUID exerciseId);
    ExerciseSchedule read(UUID id);
}
