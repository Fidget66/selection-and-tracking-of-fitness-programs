package com.makul.fitness.service.api;

import com.makul.fitness.model.ActiveProgram;

public interface ActiveProgramService {
    ActiveProgram create(ActiveProgram activeProgram);
    ActiveProgram update(ActiveProgram activeProgram);
}
