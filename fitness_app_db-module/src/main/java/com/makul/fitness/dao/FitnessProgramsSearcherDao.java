package com.makul.fitness.dao;

import com.makul.fitness.model.FitnessProgram;
import java.util.List;

public interface FitnessProgramsSearcherDao {
    List<FitnessProgram> findFitnessProgramWithRestrictions(long userId, long categoryId, int duration);
    List<FitnessProgram> findFitnessProgram(long categoryId);
}
