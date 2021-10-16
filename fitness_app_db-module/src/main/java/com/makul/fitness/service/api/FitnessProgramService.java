package com.makul.fitness.service.api;

import com.makul.fitness.model.FitnessProgram;

public interface FitnessProgramService {
    FitnessProgram create(FitnessProgram program);
    FitnessProgram read(long id);
}
