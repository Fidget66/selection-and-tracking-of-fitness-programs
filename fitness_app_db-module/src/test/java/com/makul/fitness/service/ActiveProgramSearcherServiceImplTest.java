package com.makul.fitness.service;

import com.makul.fitness.dao.ActiveProgramSearcherDao;
import com.makul.fitness.model.ActiveProgram;
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
class ActiveProgramSearcherServiceImplTest {

    @Mock
    private ActiveProgramSearcherDao searcherDao;
    @InjectMocks
    private ActiveProgramSearcherServiceImpl activeProgramService;

    @Test
    void whenRead_returnComplitedActiveProgramsList() {
        List <ActiveProgram> activeProgramList = Stream
                .generate(() -> getProgram())
                .limit(3)
                .collect(Collectors.toList());;
        Mockito.when(searcherDao.findActiveProgramsByUserIdAndIsComplitedTrue(1L)).thenReturn(activeProgramList);
        List <ActiveProgram> actual = activeProgramService.readComplitedPrograms(1);
        List <ActiveProgram> expected = activeProgramList;
        Assertions.assertEquals(expected, actual);
        Mockito.verify(searcherDao, Mockito.times(1)).findActiveProgramsByUserIdAndIsComplitedTrue(1L);
    }

    @Test
    void  whenRead_returnUncomplitedProgram() {
        ActiveProgram activeProgram = getProgram();
        Mockito.when(searcherDao.findActiveProgramsByUserIdAndIsComplitedFalse(1L)).thenReturn(activeProgram);
        ActiveProgram actual = activeProgramService.readUncomplitedProgram(1);
        ActiveProgram expected = activeProgram;
        Assertions.assertEquals(expected, actual);
        Mockito.verify(searcherDao, Mockito.times(1)).findActiveProgramsByUserIdAndIsComplitedFalse(1L);
    }

    private ActiveProgram getProgram(){
        ActiveProgram activeProgram = new ActiveProgram();
        activeProgram.setId(1);
        activeProgram.setComplited(true);
        return activeProgram;
    }
}