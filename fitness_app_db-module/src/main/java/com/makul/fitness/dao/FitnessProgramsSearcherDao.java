package com.makul.fitness.dao;

import com.makul.fitness.model.FitnessProgram;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface FitnessProgramsSearcherDao {
    Page<FitnessProgram> findFitnessProgramWithRestrictions(UUID userId, int duration, UUID categoryId, int userAge,
                                                            Pageable pageable);

    Page<FitnessProgram> findFitnessProgram(UUID categoryId, Pageable pageable);
}
