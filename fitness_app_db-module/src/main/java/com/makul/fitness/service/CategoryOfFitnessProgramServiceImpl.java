package com.makul.fitness.service;

import com.makul.fitness.dao.CategoryOfFitnessProgramDao;
import com.makul.fitness.exceptions.NoEntityException;
import com.makul.fitness.model.CategoryOfFitnessProgram;
import com.makul.fitness.service.api.CategoryOfFitnessProgramService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional(rollbackFor = Exception.class)
    public CategoryOfFitnessProgram create(CategoryOfFitnessProgram category) {
        return categoryDao.save(category);
    }

    @Override
    public List<CategoryOfFitnessProgram> readAll() {
        // ToDo переделать categoryDao.findAll() чтобы возвращал лист
        return StreamSupport.stream(categoryDao.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryOfFitnessProgram read(long id) {
        return categoryDao.findById(id).orElseThrow(()->new NoEntityException("Category Of Fitness Program"));
    }
}
