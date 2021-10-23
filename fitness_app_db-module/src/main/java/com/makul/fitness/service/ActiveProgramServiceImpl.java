package com.makul.fitness.service;

import com.makul.fitness.dao.ActiveProgramDao;
import com.makul.fitness.exceptions.IncorrectDataException;
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
    // Перенести в BusinessService
    public ActiveProgram update(ActiveProgram inputActiveProgram) {
        ActiveProgram activeProgram = read(inputActiveProgram.getId());
        activeProgram.setComplited(inputActiveProgram.isComplited());
        if (inputActiveProgram.getDays().length()>6) activeProgram.setDays(inputActiveProgram.getDays());
        if (inputActiveProgram.getScheduleList().size()>0)
            activeProgram.setScheduleList(inputActiveProgram.getScheduleList());
        return activeProgramDao.save(activeProgram);
    }

    @Override
    public ActiveProgram read(long id){
        if (id<1) throw new IncorrectDataException("Active Program id");
        return activeProgramDao.findById(id).orElseThrow(()->new NoEntityException("Active Program"));
    }
}
