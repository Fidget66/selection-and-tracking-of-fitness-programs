package com.makul.fitness.service;

import com.makul.fitness.dao.ActiveProgramSearcherDao;
import com.makul.fitness.model.ActiveProgram;
import com.makul.fitness.service.api.ActiveProgramSearcherService;
import org.springframework.stereotype.Service;
import java.util.List;

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
        return activeSearcher.findUncomplitedActiveProgram(userId);
    }
}
