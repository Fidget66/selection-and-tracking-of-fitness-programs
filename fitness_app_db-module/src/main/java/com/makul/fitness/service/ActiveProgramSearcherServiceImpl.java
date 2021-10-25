package com.makul.fitness.service;

import com.makul.fitness.dao.ActiveProgramSearcherDao;
import com.makul.fitness.exceptions.NoEntityException;
import com.makul.fitness.model.ActiveProgram;
import com.makul.fitness.service.api.ActiveProgramSearcherService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;

@Service
public class ActiveProgramSearcherServiceImpl implements ActiveProgramSearcherService {

    private final ActiveProgramSearcherDao activeSearcher;

    public ActiveProgramSearcherServiceImpl(ActiveProgramSearcherDao activeSearcher) {
        this.activeSearcher = activeSearcher;
    }

    @Override
    public List<ActiveProgram> readComplitedPrograms(long userId) {
        return activeSearcher.findComplitedActiveProgram(userId);
    }

    @Override
    public ActiveProgram readUncomplitedProgram(long userId) {
        ActiveProgram activeProgram = activeSearcher.findUncomplitedActiveProgram(userId);
        if (Objects.isNull(activeProgram)) throw new NoEntityException("Active Program");
        else return activeProgram ;
    }
}
