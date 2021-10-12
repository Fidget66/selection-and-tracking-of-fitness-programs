package com.makul.fitness.service.api;

import com.makul.fitness.model.FitnessProgram;

public interface FitnessProgramService {
    FitnessProgram readById(long id);
}
