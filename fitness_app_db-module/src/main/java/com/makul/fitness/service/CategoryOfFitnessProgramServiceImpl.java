package com.makul.fitness.service;

import com.makul.fitness.dao.CategoryOfFitnessProgramDao;
import com.makul.fitness.exceptions.NoEntityException;
import com.makul.fitness.model.CategoryOfFitnessProgram;
import com.makul.fitness.service.api.CategoryOfFitnessProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryOfFitnessProgramServiceImpl implements CategoryOfFitnessProgramService {

    private final CategoryOfFitnessProgramDao categoryDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CategoryOfFitnessProgram create(CategoryOfFitnessProgram category) {
        return categoryDao.save(category);
    }

    @Override
    public Page<CategoryOfFitnessProgram> readAll(int pageNumber, int size) {
        Pageable pageable = PageRequest.of(pageNumber, size);
        return categoryDao.findAll(pageable);
    }

    @Override
    public CategoryOfFitnessProgram read(UUID id) {
        return categoryDao.findById(id).orElseThrow(()->new NoEntityException("Category Of Fitness Program Id="+id));
    }
}
