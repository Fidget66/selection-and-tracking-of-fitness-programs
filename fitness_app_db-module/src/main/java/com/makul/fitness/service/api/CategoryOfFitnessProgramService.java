package com.makul.fitness.service.api;

import com.makul.fitness.model.CategoryOfFitnessProgram;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface CategoryOfFitnessProgramService {
    CategoryOfFitnessProgram create(CategoryOfFitnessProgram category);
    Page <CategoryOfFitnessProgram> readAll(int pageNumber, int size);
    CategoryOfFitnessProgram read(UUID id);
}
