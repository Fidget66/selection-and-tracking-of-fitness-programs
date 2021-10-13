package com.makul.fitness.service.api;

import com.makul.fitness.model.FitnessProgram;
import java.util.List;

public interface FitnessProgramService {
    FitnessProgram create(FitnessProgram program);
    FitnessProgram read(long id);
    List<FitnessProgram> readAll();
    void deleteById(long id);
}
