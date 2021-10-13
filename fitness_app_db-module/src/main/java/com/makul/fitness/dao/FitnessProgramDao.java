package com.makul.fitness.dao;

import com.makul.fitness.model.FitnessProgram;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FitnessProgramDao extends CrudRepository <FitnessProgram, Long> {
}
