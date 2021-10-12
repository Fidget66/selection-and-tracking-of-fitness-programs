package com.makul.fitness.dao;

import com.makul.fitness.model.FitnessProgram;
import org.springframework.data.repository.CrudRepository;

public interface FitnessProgramDao extends CrudRepository <FitnessProgram, Long> {
}
