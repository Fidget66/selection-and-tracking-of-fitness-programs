package com.makul.fitness.dao;

import com.makul.fitness.model.ExerciseSchedule;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExerciseScheduleDao extends CrudRepository <ExerciseSchedule, Long> {

    @Query("SELECT schedule FROM ExerciseSchedule schedule WHERE (schedule.exerciseDate = ?1)")
    List<ExerciseSchedule> findExerciseByDate(LocalDate  currentDate);
}
