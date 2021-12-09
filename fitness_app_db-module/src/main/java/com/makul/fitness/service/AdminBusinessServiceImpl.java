package com.makul.fitness.service;

import com.makul.fitness.model.CategoryOfFitnessProgram;
import com.makul.fitness.model.FitnessProgram;
import com.makul.fitness.service.api.AdminBusinessService;
import com.makul.fitness.service.api.CategoryOfFitnessProgramService;
import com.makul.fitness.service.api.FitnessProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminBusinessServiceImpl implements AdminBusinessService {

    private final CategoryOfFitnessProgramService categoryService;
    private final FitnessProgramService fitnessProgramService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FitnessProgram addFitnessProgram(UUID categoryId, FitnessProgram fitnessProgram) {
        CategoryOfFitnessProgram category = categoryService.read(categoryId);
        fitnessProgram.setCategory(category);
        return fitnessProgramService.create(fitnessProgram);
    }
}
