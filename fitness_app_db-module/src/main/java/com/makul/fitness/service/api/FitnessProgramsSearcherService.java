package com.makul.fitness.service.api;

import com.makul.fitness.model.FitnessProgram;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface FitnessProgramsSearcherService {
    Page <FitnessProgram> readFitnessProgram(UUID categoryId, int pageNumber, int size);
    Page <FitnessProgram> readFitnessProgramWithRestrictions(UUID userId, int duration, UUID categoryId,
                                                             int pageNumber, int size);
}
