package com.makul.fitness.service;

import com.makul.fitness.dao.FitnessProgramDao;
import com.makul.fitness.exceptions.IncorrectDataException;
import com.makul.fitness.exceptions.NoEntityException;
import com.makul.fitness.model.FitnessProgram;
import com.makul.fitness.service.api.FitnessProgramService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class FitnessProgramServiceImpl implements FitnessProgramService {

    private final FitnessProgramDao fitnessProgramDao;
    public FitnessProgramServiceImpl(FitnessProgramDao fitnessProgramDao) {
        this.fitnessProgramDao = fitnessProgramDao;
    }

    @Override
    public FitnessProgram create(FitnessProgram program) {
        return fitnessProgramDao.save(program);
    }

    @Override
    public FitnessProgram read(long id) {
        if (id<1) throw new IncorrectDataException("FitnessProgram id");
        return fitnessProgramDao.findById(id).orElseThrow(()->new NoEntityException("FitnessProgram"));
    }

    @Override
    public List<FitnessProgram> readAll() {
        return StreamSupport.stream(fitnessProgramDao.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
}
