package com.makul.fitness.service.api;

import com.makul.fitness.model.FitnessProgram;

import java.util.UUID;

public interface FitnessProgramService {
    FitnessProgram create(FitnessProgram program);
    FitnessProgram read(UUID id);
}
