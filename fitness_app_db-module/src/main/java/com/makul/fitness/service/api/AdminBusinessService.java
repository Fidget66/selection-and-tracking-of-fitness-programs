package com.makul.fitness.service.api;

import com.makul.fitness.model.FitnessProgram;

public interface AdminBusinessService {
    FitnessProgram addFitnessProgram(long categoryId, FitnessProgram fitnessProgram);
}
