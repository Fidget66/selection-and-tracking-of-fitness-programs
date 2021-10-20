package com.makul.fitness.service.api;

import com.makul.fitness.dto.CategoryOfFitnessProgramDto;
import com.makul.fitness.dto.FitnessProgramDto;

import java.util.List;

public interface ClientService {
    List<CategoryOfFitnessProgramDto> getCategoryOfFitnessProgram();
    List<FitnessProgramDto> getCategoryListFitnessProgram(long categoryId);
    FitnessProgramDto getFitnessProgram(long programId);
    List<FitnessProgramDto> getFitnessProgramWithRestrictions(int duration);
}
