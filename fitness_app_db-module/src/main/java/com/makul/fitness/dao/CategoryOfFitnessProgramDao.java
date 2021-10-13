package com.makul.fitness.dao;

import com.makul.fitness.model.CategoryOfFitnessProgram;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryOfFitnessProgramDao extends CrudRepository <CategoryOfFitnessProgram, Long> {
}
