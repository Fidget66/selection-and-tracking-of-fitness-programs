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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
                .collect(Collectors.toList());
        UUID uuid = UUID.randomUUID();
        Pageable pageable = PageRequest.of(0,30);
        Page<ActiveProgram> page = new PageImpl<>(activeProgramList,pageable,3);
        Mockito.when(searcherDao.findActiveProgramsByUserIdAndIsComplitedTrue(uuid,pageable)).thenReturn(page);
        List <ActiveProgram> actual = activeProgramService.readComplitedPrograms(uuid,0,30).getContent();
        List <ActiveProgram> expected = activeProgramList;
        Assertions.assertEquals(expected, actual);
        Mockito.verify(searcherDao, Mockito.times(1)).findActiveProgramsByUserIdAndIsComplitedTrue(uuid,pageable);
    }

    @Test
    void  whenRead_returnUncomplitedProgram() {
        ActiveProgram activeProgram = getProgram();
        UUID uuid = UUID.randomUUID();
        activeProgram.setId(uuid);
        Mockito.when(searcherDao.findActiveProgramsByUserIdAndIsComplitedFalse(uuid)).thenReturn(Optional.of(activeProgram));
        ActiveProgram actual = activeProgramService.readUncomplitedProgram(uuid);
        ActiveProgram expected = activeProgram;
        Assertions.assertEquals(expected, actual);
        Mockito.verify(searcherDao, Mockito.times(1)).findActiveProgramsByUserIdAndIsComplitedFalse(uuid);
    }

    private ActiveProgram getProgram(){
        ActiveProgram activeProgram = new ActiveProgram();
        activeProgram.setComplited(true);
        return activeProgram;
    }
}