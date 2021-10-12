package com.makul.fitness.service;

import com.makul.fitness.dao.CategoryOfFitnessProgramDao;
import com.makul.fitness.model.CategoryOfFitnessProgram;
import com.makul.fitness.service.api.CategoryOfFitnessProgramService;

public class CategoryOfFitnessProgramServiceImpl implements CategoryOfFitnessProgramService {

    private final CategoryOfFitnessProgramDao categoryDao;

    public CategoryOfFitnessProgramServiceImpl(CategoryOfFitnessProgramDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    public CategoryOfFitnessProgram create(CategoryOfFitnessProgram category) {
        return categoryDao.save(category);
    }

    @Override
    public void deleteById(long id) {
        categoryDao.deleteById(id);
    }
}
