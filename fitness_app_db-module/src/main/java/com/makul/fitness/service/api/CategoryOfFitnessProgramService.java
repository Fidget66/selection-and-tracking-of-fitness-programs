package com.makul.fitness.service.api;

import com.makul.fitness.model.CategoryOfFitnessProgram;

public interface CategoryOfFitnessProgramService {
    CategoryOfFitnessProgram create(CategoryOfFitnessProgram category);

    void deleteById(long id);
}
