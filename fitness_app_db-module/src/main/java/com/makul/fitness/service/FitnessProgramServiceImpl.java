package com.makul.fitness.service;

import com.makul.fitness.dao.FitnessProgramDao;
import com.makul.fitness.exceptions.NoEntityException;
import com.makul.fitness.model.FitnessProgram;
import com.makul.fitness.service.api.FitnessProgramService;

public class FitnessProgramServiceImpl implements FitnessProgramService {

    private final FitnessProgramDao fitnessProgramDao;

    public FitnessProgramServiceImpl(FitnessProgramDao fitnessProgramDao) {
        this.fitnessProgramDao = fitnessProgramDao;
    }

    @Override
    public FitnessProgram readById(long id) {
        return fitnessProgramDao.findById(id).orElseThrow(()->new NoEntityException("FitnessProgram"));
    }
}
