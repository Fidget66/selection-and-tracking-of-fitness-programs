package com.makul.fitness.service;

import com.makul.fitness.dao.ActiveProgramDao;
import com.makul.fitness.exceptions.NoEntityException;
import com.makul.fitness.model.ActiveProgram;
import com.makul.fitness.service.api.ActiveProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ActiveProgramServiceImpl implements ActiveProgramService {

    private final ActiveProgramDao activeProgramDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ActiveProgram create(ActiveProgram activeProgram) {
        return activeProgramDao.save(activeProgram);
    }

    @Override
    public ActiveProgram read(UUID id) {
        return activeProgramDao.findById(id).orElseThrow(() -> new NoEntityException("Active Program"));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ActiveProgram update(ActiveProgram inputActiveProgram) {
        ActiveProgram activeProgram = read(inputActiveProgram.getId());
        activeProgram.setComplited(inputActiveProgram.isComplited());
        if (Objects.nonNull(inputActiveProgram.getDays()) && inputActiveProgram.getDays().length() > 6) {
            activeProgram.setDays(inputActiveProgram.getDays());
        }
        if (CollectionUtils.isEmpty(inputActiveProgram.getScheduleList())) {
            activeProgram.setScheduleList(inputActiveProgram.getScheduleList());
        }
        return activeProgram;
    }
}
