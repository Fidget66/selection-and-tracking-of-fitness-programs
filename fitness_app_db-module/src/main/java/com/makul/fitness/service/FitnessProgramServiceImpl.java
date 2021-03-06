package com.makul.fitness.service;

import com.makul.fitness.dao.FitnessProgramDao;
import com.makul.fitness.exceptions.NoEntityException;
import com.makul.fitness.model.FitnessProgram;
import com.makul.fitness.service.api.FitnessProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FitnessProgramServiceImpl implements FitnessProgramService {

    private final FitnessProgramDao fitnessProgramDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FitnessProgram create(FitnessProgram program) {
        return fitnessProgramDao.save(program);
    }

    @Override
    public FitnessProgram read(UUID id) {
        return fitnessProgramDao.findById(id).orElseThrow(()->new NoEntityException("Fitness Program Id=" + id));
    }
}
