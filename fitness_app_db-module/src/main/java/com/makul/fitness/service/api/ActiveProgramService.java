package com.makul.fitness.service.api;

import com.makul.fitness.model.ActiveProgram;

import java.util.UUID;

public interface ActiveProgramService {
    ActiveProgram create(ActiveProgram activeProgram);
    ActiveProgram read(UUID id);
    ActiveProgram update(ActiveProgram inputActiveProgram);
}
