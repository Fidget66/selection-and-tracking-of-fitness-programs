package com.makul.fitness.dao;

import com.makul.fitness.model.FitnessProgram;
import java.util.List;

public interface FitnessProgramsSearcherDao {
    List<FitnessProgram> findFitnessProgramWithRestrictions(long userId, int duration, long categoryId);
    List<FitnessProgram> findFitnessProgram(long categoryId);
}
