package com.makul.fitness.service;

import com.makul.fitness.dao.ActiveProgramDao;
import com.makul.fitness.exceptions.NoEntityException;
import com.makul.fitness.model.ActiveProgram;
import com.makul.fitness.service.api.ActiveProgramService;
import org.springframework.stereotype.Service;

@Service
public class ActiveProgramServiceImpl implements ActiveProgramService {

    private final ActiveProgramDao activeProgramDao;
    public ActiveProgramServiceImpl(ActiveProgramDao activeProgramDao) {
        this.activeProgramDao = activeProgramDao;
    }

    @Override
    public ActiveProgram create(ActiveProgram activeProgram) {
        return activeProgramDao.save(activeProgram);
    }

    @Override
    public ActiveProgram update(ActiveProgram inputActiveProgram) {
        ActiveProgram activeProgram = activeProgramDao
                .findById(inputActiveProgram.getId())
                .orElseThrow(()->new NoEntityException("ActiveProgram"));
        activeProgram.setComplited(inputActiveProgram.isComplited());
        return activeProgramDao.save(activeProgram);
    }
}
