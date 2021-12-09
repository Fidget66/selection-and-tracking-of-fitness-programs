package com.makul.fitness.service;

import com.makul.fitness.dao.FitnessProgramsSearcherDao;
import com.makul.fitness.model.FitnessProgram;
import com.makul.fitness.model.Users;
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

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
class FitnessProgramsSearcherServiceImplTest {

    @Mock
    private FitnessProgramsSearcherDao fitnessProgramsSearcherDao;
    @Mock
    private UsersServiceImpl usersService;
    @InjectMocks
    private FitnessProgramsSearcherServiceImpl fitnessProgramsSearcherService;


    @Test
    void whenRead_returnListFitnessProgram() {
        List <FitnessProgram> fitnessProgramList = Stream
                .generate(() -> getProgram())
                .limit(3)
                .collect(Collectors.toList());
        UUID uuid = getUUID();
        Pageable pageable = PageRequest.of(0,30);
        Page <FitnessProgram> page = new PageImpl<>(fitnessProgramList,pageable,30);
        Mockito.when(fitnessProgramsSearcherDao.findFitnessProgram(uuid,pageable)).thenReturn(page);
        List <FitnessProgram> actual = fitnessProgramsSearcherService.readFitnessProgram(uuid,0,30).getContent();
        List <FitnessProgram> expected = fitnessProgramList;
        Assertions.assertEquals(expected, actual);
        Mockito.verify(fitnessProgramsSearcherDao,Mockito.times(1)).findFitnessProgram(uuid,pageable);
    }

    @Test
    void whenRead_returnListFitnessProgramWithRestrictions() {
        List <FitnessProgram> fitnessProgramList = Stream
                .generate(() -> getProgram())
                .limit(3)
                .collect(Collectors.toList());
        UUID uuid = getUUID();
        Pageable pageable = PageRequest.of(0,30);
        Page <FitnessProgram> page = new PageImpl<>(fitnessProgramList,pageable,30);
        Mockito.when(usersService.read(uuid))
                .thenReturn(getUser());
        Mockito.when(fitnessProgramsSearcherDao.findFitnessProgramWithRestrictions(uuid, 30, uuid,3,pageable))
                .thenReturn(page);
        List <FitnessProgram> actual = fitnessProgramsSearcherService.readFitnessProgramWithRestrictions(uuid,30, uuid,0,30).getContent();
        List <FitnessProgram> expected = fitnessProgramList;
        Assertions.assertEquals(expected, actual);
        Mockito.verify(fitnessProgramsSearcherDao,Mockito.times(1))
                .findFitnessProgramWithRestrictions(uuid,  30, uuid,3,pageable);
    }

    private FitnessProgram getProgram(){
        FitnessProgram fitnessProgram = new FitnessProgram();
        fitnessProgram.setDuration((short) 30);
        fitnessProgram.setAgeRestriction((byte) 50);
        fitnessProgram.setShortName("Test Fitness Program");
        return fitnessProgram;
    }

    private Users getUser(){
        Users users = new Users();
        users.setDateOfBirth(LocalDate.now().minusYears(3));
        return users;
    }

    private UUID getUUID(){
        return UUID.randomUUID();
    }
}