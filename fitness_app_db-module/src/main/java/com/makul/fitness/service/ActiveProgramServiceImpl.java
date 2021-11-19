package com.makul.fitness.service;

import com.makul.fitness.dao.ActiveProgramDao;
import com.makul.fitness.exceptions.NoEntityException;
import com.makul.fitness.model.ActiveProgram;
import com.makul.fitness.service.api.ActiveProgramService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class ActiveProgramServiceImpl implements ActiveProgramService {

    private final ActiveProgramDao activeProgramDao;
    public ActiveProgramServiceImpl(ActiveProgramDao activeProgramDao) {
        this.activeProgramDao = activeProgramDao;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ActiveProgram create(ActiveProgram activeProgram) {
        return activeProgramDao.save(activeProgram);
    }

    @Override
    public ActiveProgram read(long id){
        return activeProgramDao.findById(id).orElseThrow(()->new NoEntityException("Active Program"));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ActiveProgram update(ActiveProgram inputActiveProgram) {
        ActiveProgram activeProgram = read(inputActiveProgram.getId());
        activeProgram.setComplited(inputActiveProgram.isComplited());
        if (Objects.nonNull(inputActiveProgram.getDays()) && inputActiveProgram.getDays().length()>6) activeProgram.setDays(inputActiveProgram.getDays());
        if (Objects.nonNull(inputActiveProgram.getScheduleList()) && inputActiveProgram.getScheduleList().size()>0)
            activeProgram.setScheduleList(inputActiveProgram.getScheduleList());
        return activeProgram;
    }
}
