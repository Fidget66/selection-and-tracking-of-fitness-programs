package com.makul.fitness.service;

import com.makul.fitness.dao.FitnessProgramsSearcherDao;
import com.makul.fitness.model.FitnessProgram;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
class FitnessProgramsSearcherServiceImplTest {

    @Mock
    private FitnessProgramsSearcherDao fitnessProgramsSearcherDao;
    @InjectMocks
    private FitnessProgramsSearcherServiceImpl fitnessProgramsSearcherService;

    @Test
    void whenRead_returnListFitnessProgram() {
        List <FitnessProgram> fitnessProgramList = Stream
                .generate(() -> getProgram())
                .limit(3)
                .collect(Collectors.toList());
        Mockito.when(fitnessProgramsSearcherDao.findFitnessProgram(1L)).thenReturn(fitnessProgramList);
        List <FitnessProgram> actual = fitnessProgramsSearcherService.readFitnessProgram(1);
        List <FitnessProgram> expected = fitnessProgramList;
        Assertions.assertEquals(expected, actual);
        Mockito.verify(fitnessProgramsSearcherDao,Mockito.times(1)).findFitnessProgram(1L);
    }

    @Test
    void whenRead_returnListFitnessProgramWithRestrictions() {
        List <FitnessProgram> fitnessProgramList = Stream
                .generate(() -> getProgram())
                .limit(3)
                .collect(Collectors.toList());
        Mockito.when(fitnessProgramsSearcherDao.findFitnessProgramWithRestrictions(1L,2L, 30))
                .thenReturn(fitnessProgramList);
        List <FitnessProgram> actual = fitnessProgramsSearcherService.readFitnessProgramWithRestrictions(1,2,30);
        List <FitnessProgram> expected = fitnessProgramList;
        Assertions.assertEquals(expected, actual);
        Mockito.verify(fitnessProgramsSearcherDao,Mockito.times(1))
                .findFitnessProgramWithRestrictions(1L, 2L, 30);
    }

    private FitnessProgram getProgram(){
        FitnessProgram fitnessProgram = new FitnessProgram();
        fitnessProgram.setDuration((short) 30);
        fitnessProgram.setAgeRestriction((byte) 50);
        fitnessProgram.setShortName("Test Fitness Program");
        return fitnessProgram;
    }
}