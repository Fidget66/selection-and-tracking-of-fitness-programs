package com.makul.fitness.service.api;

import com.makul.fitness.model.ExerciseSchedule;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ExerciseScheduleSearcherService {
    List <ExerciseSchedule> readExerciseByDate(LocalDate currentDate);
    Page <ExerciseSchedule> readExerciseByActiveProgramId(UUID activeProgramId, int pageNumber, int size);
}
