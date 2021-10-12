package com.makul.fitness.dao;

import com.makul.fitness.model.ExerciseSchedule;
import org.springframework.data.repository.CrudRepository;

public interface ExerciseScheduleDao extends CrudRepository <ExerciseSchedule, Long> {
}
