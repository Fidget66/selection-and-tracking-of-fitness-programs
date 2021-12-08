package com.makul.fitness.service;

import com.makul.fitness.dao.ExerciseScheduleSearcherDao;
import com.makul.fitness.model.ExerciseSchedule;
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
class ExerciseScheduleSearcherServiceImplTest {
    @Mock
    private ExerciseScheduleSearcherDao searcherDao;
    @InjectMocks
    private ExerciseScheduleSearcherServiceImpl scheduleService;

    @Test
    void readExerciseByActiveProgramId() {
        List<ExerciseSchedule> scheduleList = Stream
                .generate(() -> getSchedule())
                .limit(4)
                .collect(Collectors.toList());
        UUID uuid = getUUID();
        Pageable pageable = PageRequest.of(0,30);
        Page<ExerciseSchedule> page = new PageImpl<>(scheduleList,pageable,3);
        Mockito.when(searcherDao.findByActiveProgram_IdOrderByExerciseDateAsc(uuid, pageable)).thenReturn(page);
        List <ExerciseSchedule> actual = scheduleService.readExerciseByActiveProgramId(uuid,0,30).getContent();
        List <ExerciseSchedule> expected = scheduleList;
        Assertions.assertEquals(expected, actual);
        Mockito.verify(searcherDao, Mockito.times(1)).findByActiveProgram_IdOrderByExerciseDateAsc(uuid,pageable);
    }
    @Test
    void readExerciseByDate() {
        List<ExerciseSchedule> scheduleList = Stream
                .generate(() -> getSchedule())
                .limit(3)
                .collect(Collectors.toList());
        LocalDate currentDate = LocalDate.now();
        Mockito.when(searcherDao.findByExerciseDate(currentDate)).thenReturn(scheduleList);
        List <ExerciseSchedule> actual = scheduleService.readExerciseByDate(currentDate);
        List <ExerciseSchedule> expected = scheduleList;
        Assertions.assertEquals(expected, actual);
        Mockito.verify(searcherDao, Mockito.times(1)).findByExerciseDate(currentDate);
    }

    private ExerciseSchedule getSchedule(){
        ExerciseSchedule schedule = new ExerciseSchedule();
        schedule.setId(getUUID());
        schedule.setExerciseDate(LocalDate.of(2021, 10,18));
        schedule.setComplited(true);
        return schedule;
    }

    private UUID getUUID(){
        return UUID.randomUUID();
    }
}