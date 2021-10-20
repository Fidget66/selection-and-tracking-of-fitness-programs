package com.makul.fitness.service.api;

import com.makul.fitness.model.FitnessProgram;
import java.util.List;

public interface FitnessProgramsSearcherService {
    List<FitnessProgram> readFitnessProgram(long categoryId);
    List<FitnessProgram> readFitnessProgramWithRestrictions(long userId, int duration);
}
