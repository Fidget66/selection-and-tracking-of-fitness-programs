package com.makul.fitness.dao;

import com.makul.fitness.model.FitnessProgram;
import java.util.List;

public interface FitnessProgramsSearcherDao {
    List<FitnessProgram> findFitnessProgramWithRestrictions(long userId, int duration);
    List<FitnessProgram> findFitnessProgram(long categoryId);
}
