package com.makul.fitness.service.api;

import com.makul.fitness.model.FitnessProgram;

import java.util.UUID;

public interface AdminBusinessService {
    FitnessProgram addFitnessProgram(UUID categoryId, FitnessProgram fitnessProgram);
}
