package com.makul.fitness.service.api;

import com.makul.fitness.model.ActiveProgram;
import java.util.List;

public interface ActiveProgramSearcherService {
    List<ActiveProgram>  readComplitedPrograms(long userId);
    ActiveProgram readUncomplitedProgram(long userId);
}
