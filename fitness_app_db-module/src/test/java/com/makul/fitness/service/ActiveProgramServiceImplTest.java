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
        Mockito.when(activeProgramDao.findById(1L)).thenReturn(Optional.ofNullable(activeProgram));
        ActiveProgram actual = activeProgramService.read(1);
        ActiveProgram expected = activeProgram;
        Assertions.assertEquals(expected, actual);
        Mockito.verify(activeProgramDao, Mockito.times(1)).findById(1L);
    }

    @Test
    void whenRead_throwException() {
        Mockito.when(activeProgramDao.findById(4L)).thenReturn(Optional.empty());
        NoEntityException noEntityException = Assertions.assertThrows(NoEntityException.class,
                ()->activeProgramService.read(4));
        Assertions.assertEquals(noEntityException.getMessage(),
                "Такой записи для Active Program в базе данных не существует");
        Mockito.verify(activeProgramDao, Mockito.times(1)).findById(4L);
    }

    private ActiveProgram getActiveProgram(){
        ActiveProgram activeProgram = new ActiveProgram();
        activeProgram.setId(1);
        return activeProgram;
    }
}