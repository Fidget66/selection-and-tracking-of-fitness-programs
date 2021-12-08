package com.makul.fitness.service;

import com.makul.fitness.dao.ActiveProgramSearcherDao;
import com.makul.fitness.exceptions.NoEntityException;
import com.makul.fitness.model.ActiveProgram;
import com.makul.fitness.service.api.ActiveProgramSearcherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ActiveProgramSearcherServiceImpl implements ActiveProgramSearcherService {

    private final ActiveProgramSearcherDao activeSearcher;

    @Override
    public Page<ActiveProgram> readComplitedPrograms(UUID userId, int pageNumber , int size) {
        Pageable pageable = PageRequest.of(pageNumber, size);
        return activeSearcher.findActiveProgramsByUserIdAndIsComplitedTrue(userId, pageable);
    }

    @Override
    public ActiveProgram readUncomplitedProgram(UUID userId) {
        ActiveProgram activeProgram = activeSearcher.findActiveProgramsByUserIdAndIsComplitedFalse(userId).
                orElseThrow(() -> new NoEntityException("Active Program for userId="+userId));
        return activeProgram;
    }
}
