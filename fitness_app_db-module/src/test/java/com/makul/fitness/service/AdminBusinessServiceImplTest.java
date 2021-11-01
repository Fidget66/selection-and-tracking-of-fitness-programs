package com.makul.fitness.service;

import com.makul.fitness.model.CategoryOfFitnessProgram;
import com.makul.fitness.model.FitnessProgram;
import com.makul.fitness.service.api.CategoryOfFitnessProgramService;
import com.makul.fitness.service.api.FitnessProgramService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AdminBusinessServiceImplTest {

    @Mock
    private CategoryOfFitnessProgramService categoryService;
    @Mock
    private FitnessProgramService fitnessProgramService;
    @InjectMocks
    private AdminBusinessServiceImpl adminBusinessService;

    @Test
    void whenAddFitnessProgram_thenOk() {
        FitnessProgram fitnessProgram = getFitnessProgram();
        CategoryOfFitnessProgram category = getCategory();
        Mockito.when(categoryService.read(3L)).thenReturn(category);
        Mockito.when(fitnessProgramService.create(fitnessProgram)).thenReturn(fitnessProgram);
        FitnessProgram actual = adminBusinessService.addFitnessProgram(3, fitnessProgram);
        FitnessProgram expected = fitnessProgram;
        Assertions.assertEquals(expected, actual);
        Mockito.verify(categoryService, Mockito.times(1)).read(3L);
        Mockito.verify(fitnessProgramService, Mockito.times(1)).create(fitnessProgram);
    }

    private FitnessProgram getFitnessProgram(){
        FitnessProgram fitnessProgram = new FitnessProgram();
        fitnessProgram.setShortName("Test");
        fitnessProgram.setDescription("Test description");
        fitnessProgram.setAgeRestriction(30);
        fitnessProgram.setExercisePerWeek(3);
        fitnessProgram.setWeightRestriction(66);
        fitnessProgram.setDuration(3);
        return fitnessProgram;
    }

    private CategoryOfFitnessProgram getCategory(){
        return new CategoryOfFitnessProgram();
    }
}