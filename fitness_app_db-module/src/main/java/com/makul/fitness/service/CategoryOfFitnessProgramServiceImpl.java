package com.makul.fitness.service;

import com.makul.fitness.dao.CategoryOfFitnessProgramDao;
import com.makul.fitness.exceptions.IncorrectDataException;
import com.makul.fitness.exceptions.NoEntityException;
import com.makul.fitness.model.CategoryOfFitnessProgram;
import com.makul.fitness.service.api.CategoryOfFitnessProgramService;
import org.springframework.stereotype.Service;
import java.util.List;
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
    public CategoryOfFitnessProgram read(long id) {
        if (id<1) throw new IncorrectDataException("category of fitness program id");
        return categoryDao.findById(id).orElseThrow(()->new NoEntityException("Category"));
    }

    @Override
    public List<CategoryOfFitnessProgram> readAll() {
        return StreamSupport.stream(categoryDao.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(long id) {
        if (id<1) throw new IncorrectDataException("category of fitness program id");
        categoryDao.deleteById(id);
    }
}
