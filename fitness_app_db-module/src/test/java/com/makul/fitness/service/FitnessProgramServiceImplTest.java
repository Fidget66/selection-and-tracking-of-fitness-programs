package com.makul.fitness.service;

import com.makul.fitness.dao.FitnessProgramDao;
import com.makul.fitness.exceptions.NoEntityException;
import com.makul.fitness.model.FitnessProgram;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class FitnessProgramServiceImplTest {

    @Mock
    private FitnessProgramDao fitnessProgramDao;
    @InjectMocks
    private FitnessProgramServiceImpl programService;

    @Test
    void whenCreate_returnFitnessProgram() {
        FitnessProgram fitnessProgram = getProgram();
        Mockito.when(fitnessProgramDao.save(fitnessProgram)).thenReturn(fitnessProgram);
        FitnessProgram actual = programService.create(fitnessProgram);
        FitnessProgram expected = fitnessProgram;
        Assertions.assertEquals(expected, actual);
        Mockito.verify(fitnessProgramDao, Mockito.times(1)).save(fitnessProgram);
    }

    @Test
    void whenRead_returnFitnessProgram() {
        FitnessProgram fitnessProgram = getProgram();
        UUID uuid = getUUID();
        Mockito.when(fitnessProgramDao.findById(uuid)).thenReturn(Optional.ofNullable(fitnessProgram));
        FitnessProgram actual = programService.read(uuid);
        FitnessProgram expected = fitnessProgram;
        Assertions.assertEquals(expected, actual);
        Mockito.verify(fitnessProgramDao, Mockito.times(1)).findById(uuid);
    }

    @Test
        void whenRead_throwException() {
        FitnessProgram fitnessProgram = getProgram();
        UUID uuid = getUUID();
        Mockito.when(fitnessProgramDao.findById(uuid)).thenReturn(Optional.empty());
        NoEntityException noEntityException = Assertions.assertThrows(NoEntityException.class,
                ()->programService.read(uuid));
        Assertions.assertEquals(noEntityException.getMessage(),
                "Такой записи для Fitness Program в базе данных не существует");
        Mockito.verify(fitnessProgramDao, Mockito.times(1)).findById(uuid);
    }

    private FitnessProgram getProgram(){
        FitnessProgram fitnessProgram = new FitnessProgram();
        fitnessProgram.setDuration((short) 30);
        fitnessProgram.setAgeRestriction((byte) 50);
        fitnessProgram.setShortName("Test Fitness Program");
        return fitnessProgram;
    }

    private UUID getUUID(){
        return UUID.randomUUID();
    }
}