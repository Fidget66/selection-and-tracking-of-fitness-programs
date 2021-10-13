package com.makul.fitness.service;

import com.makul.fitness.dao.ActiveProgramDao;
import com.makul.fitness.exceptions.IncorrectDataException;
import com.makul.fitness.exceptions.NoEntityException;
import com.makul.fitness.model.ActiveProgram;
import com.makul.fitness.service.api.ActiveProgramService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
    public ActiveProgram read(long id) {
        if (id<1) throw new IncorrectDataException("ActiveProgram id");
        return activeProgramDao.findById(id).orElseThrow(()->new NoEntityException("ActiveProgram"));
    }

    @Override
    public List<ActiveProgram> readAll() {
        return StreamSupport.stream(activeProgramDao.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(long id) {
        if (id<1) throw new IncorrectDataException("ActiveProgram id");
        activeProgramDao.deleteById(id);

    }
}
