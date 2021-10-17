package com.makul.fitness.service;

import com.makul.fitness.dao.ActiveProgramDao;
import com.makul.fitness.exceptions.NoEntityException;
import com.makul.fitness.model.ActiveProgram;
import com.makul.fitness.service.api.ActiveProgramService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ActiveProgramServiceImpl implements ActiveProgramService {

    private final ActiveProgramDao activeProgramDao;
    public ActiveProgramServiceImpl(ActiveProgramDao activeProgramDao) {
        this.activeProgramDao = activeProgramDao;
    }

    @Override
    @Transactional
    public ActiveProgram create(ActiveProgram activeProgram) {
        return activeProgramDao.save(activeProgram);
    }

    @Override
    @Transactional
    public ActiveProgram update(ActiveProgram inputActiveProgram) {
        ActiveProgram activeProgram = activeProgramDao
                .findById(inputActiveProgram.getId())
                .orElseThrow(()->new NoEntityException("ActiveProgram"));
        activeProgram.setComplited(inputActiveProgram.isComplited());
        if (inputActiveProgram.getScheduleList().size()>0)
            activeProgram.setScheduleList(inputActiveProgram.getScheduleList());
        return activeProgramDao.save(activeProgram);
    }

    @Override
    public ActiveProgram read(long id){
        return activeProgramDao.findById(id).orElseThrow();
    }
}