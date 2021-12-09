package com.makul.fitness.service;

import com.makul.fitness.dao.ActiveProgramDao;
import com.makul.fitness.exceptions.NoEntityException;
import com.makul.fitness.model.ActiveProgram;
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
class ActiveProgramServiceImplTest {

    @Mock
    private ActiveProgramDao activeProgramDao;
    @InjectMocks
    private ActiveProgramServiceImpl activeProgramService;

    @Test
    void whenCreate_returnActiveProgram() {
        ActiveProgram activeProgram = getActiveProgram();
        Mockito.when(activeProgramDao.save(activeProgram)).thenReturn(activeProgram);
        ActiveProgram actual = activeProgramService.create(activeProgram);
        ActiveProgram expected = activeProgram;
        Assertions.assertEquals(expected, actual);
        Mockito.verify(activeProgramDao, Mockito.times(1)).save(activeProgram);
    }

    @Test
    void whenRead_returnActiveProgram() {
        ActiveProgram activeProgram = getActiveProgram();
        UUID uuid = getUUID();
        Mockito.when(activeProgramDao.findById(uuid)).thenReturn(Optional.ofNullable(activeProgram));
        ActiveProgram actual = activeProgramService.read(uuid);
        ActiveProgram expected = activeProgram;
        Assertions.assertEquals(expected, actual);
        Mockito.verify(activeProgramDao, Mockito.times(1)).findById(uuid);
    }

    @Test
    void whenRead_throwException() {
        UUID uuid = getUUID();
        Mockito.when(activeProgramDao.findById(uuid)).thenReturn(Optional.empty());
        NoEntityException noEntityException = Assertions.assertThrows(NoEntityException.class,
                ()->activeProgramService.read(uuid));
        Assertions.assertEquals(noEntityException.getMessage(),
                "Такой записи для Active Program в базе данных не существует");
        Mockito.verify(activeProgramDao, Mockito.times(1)).findById(uuid);
    }

    @Test
    void whenUpdate_returnActiveProgram() {
        ActiveProgram activeProgram = getActiveProgram();
        UUID uuid = activeProgram.getId();
        Mockito.when(activeProgramDao.findById(uuid)).thenReturn(Optional.ofNullable(activeProgram));
        ActiveProgram actual = activeProgramService.update(activeProgram);
        ActiveProgram expected = activeProgram;
        Assertions.assertEquals(expected, actual);
        Mockito.verify(activeProgramDao, Mockito.times(1)).findById(uuid);
    }

    private ActiveProgram getActiveProgram(){
        ActiveProgram activeProgram = new ActiveProgram();
        activeProgram.setId(getUUID());
        return activeProgram;
    }

    private UUID getUUID(){
        return UUID.randomUUID();
    }
}