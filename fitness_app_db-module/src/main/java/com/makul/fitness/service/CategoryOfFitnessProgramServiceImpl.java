package com.makul.fitness.service;

import com.makul.fitness.dao.CategoryOfFitnessProgramDao;
import com.makul.fitness.exceptions.IncorrectDataException;
import com.makul.fitness.exceptions.NoEntityException;
import com.makul.fitness.model.CategoryOfFitnessProgram;
import com.makul.fitness.model.FitnessProgram;
import com.makul.fitness.service.api.CategoryOfFitnessProgramService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
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
    public List<CategoryOfFitnessProgram> readAll() {
        return StreamSupport.stream(categoryDao.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryOfFitnessProgram update(CategoryOfFitnessProgram category) {
        CategoryOfFitnessProgram outputCategory = categoryDao.findById(category.getId())
                .orElseThrow(()->new NoEntityException("Category"));
        if (Objects.nonNull(category.getFitnessPrograms()) && !category.getFitnessPrograms().isEmpty())
            for (FitnessProgram program: category.getFitnessPrograms()){
                outputCategory.getFitnessPrograms().add(program);
            }
        return categoryDao.save(outputCategory);
    }

    @Override
    public CategoryOfFitnessProgram read(long id) {
        if (id<1) throw new IncorrectDataException("Category Of FitnessProgram id");
        return categoryDao.findById(id).orElseThrow(()->new NoEntityException("Category Of Fitness Program"));
    }
}
