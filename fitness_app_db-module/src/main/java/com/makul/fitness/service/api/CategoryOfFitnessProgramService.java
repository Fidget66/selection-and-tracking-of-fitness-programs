package com.makul.fitness.service.api;

import com.makul.fitness.model.CategoryOfFitnessProgram;
import java.util.List;

public interface CategoryOfFitnessProgramService {
    CategoryOfFitnessProgram create(CategoryOfFitnessProgram category);
    List<CategoryOfFitnessProgram> readAll();
    CategoryOfFitnessProgram read(long id);
}
