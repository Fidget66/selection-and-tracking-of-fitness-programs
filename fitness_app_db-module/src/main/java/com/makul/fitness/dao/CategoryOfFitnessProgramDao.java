package com.makul.fitness.dao;

import com.makul.fitness.model.CategoryOfFitnessProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryOfFitnessProgramDao extends JpaRepository <CategoryOfFitnessProgram, UUID> {
}
