package com.makul.fitness.service.api;

import com.makul.fitness.model.ActiveProgram;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface ActiveProgramSearcherService {
    Page<ActiveProgram> readComplitedPrograms(UUID userId, int page, int size);
    ActiveProgram readUncomplitedProgram(UUID userId);
}
