package com.makul.fitness.dao;

import com.makul.fitness.model.FitnessProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FitnessProgramDao extends JpaRepository <FitnessProgram, UUID> {
}
