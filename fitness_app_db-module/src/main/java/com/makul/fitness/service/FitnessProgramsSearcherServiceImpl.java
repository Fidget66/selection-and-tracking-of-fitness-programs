package com.makul.fitness.service;

import com.makul.fitness.dao.FitnessProgramsSearcherDao;
import com.makul.fitness.model.FitnessProgram;
import com.makul.fitness.service.api.FitnessProgramsSearcherService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FitnessProgramsSearcherServiceImpl implements FitnessProgramsSearcherService {

    private final FitnessProgramsSearcherDao fitnessSearcherDao;

    public FitnessProgramsSearcherServiceImpl(FitnessProgramsSearcherDao fitnessSearcherDao) {
        this.fitnessSearcherDao = fitnessSearcherDao;
    }

    @Override
    public List<FitnessProgram> readFitnessProgram(long categoryId) {
        return fitnessSearcherDao.findFitnessProgram(categoryId);
    }

    @Override
    public List<FitnessProgram> readFitnessProgramWithRestrictions(long userId, int duration) {
        return fitnessSearcherDao.findFitnessProgramWithRestrictions(userId,duration);
    }
}
