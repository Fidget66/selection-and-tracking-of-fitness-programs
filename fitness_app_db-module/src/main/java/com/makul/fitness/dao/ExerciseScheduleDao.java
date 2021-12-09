package com.makul.fitness.dao;

import com.makul.fitness.model.ExerciseSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ExerciseScheduleDao extends JpaRepository <ExerciseSchedule, UUID> {
}