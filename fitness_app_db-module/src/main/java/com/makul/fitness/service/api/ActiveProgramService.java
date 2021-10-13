package com.makul.fitness.service.api;

import com.makul.fitness.model.ActiveProgram;
import java.util.List;

public interface ActiveProgramService {
    ActiveProgram create(ActiveProgram activeProgram);
    ActiveProgram read(long id);
    List<ActiveProgram> readAll();
    void deleteById(long id);
}
