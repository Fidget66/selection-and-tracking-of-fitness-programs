package com.makul.fitness.dao;

import com.makul.fitness.model.ExerciseSchedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface ExerciseScheduleSearcherDao extends JpaRepository <ExerciseSchedule, UUID> {

    List <ExerciseSchedule> findByExerciseDate(LocalDate currentDate);

    Page <ExerciseSchedule> findByActiveProgram_IdOrderByExerciseDateAsc(UUID activeProgramId, Pageable pageable);
}
