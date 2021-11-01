package com.makul.fitness.service;

import com.makul.fitness.model.CategoryOfFitnessProgram;
import com.makul.fitness.model.FitnessProgram;
import com.makul.fitness.service.api.AdminBusinessService;
import com.makul.fitness.service.api.CategoryOfFitnessProgramService;
import com.makul.fitness.service.api.FitnessProgramService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminBusinessServiceImpl implements AdminBusinessService {

    private final CategoryOfFitnessProgramService categoryService;
    private final FitnessProgramService fitnessProgramService;

    public AdminBusinessServiceImpl(CategoryOfFitnessProgramService categoryService, FitnessProgramService fitnessProgramService) {
        this.categoryService = categoryService;
        this.fitnessProgramService = fitnessProgramService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FitnessProgram addFitnessProgram(long categoryId, FitnessProgram fitnessProgram) {
        CategoryOfFitnessProgram category = categoryService.read(categoryId);
        fitnessProgram.setCategory(category);
        return fitnessProgramService.create(fitnessProgram);
    }
}
