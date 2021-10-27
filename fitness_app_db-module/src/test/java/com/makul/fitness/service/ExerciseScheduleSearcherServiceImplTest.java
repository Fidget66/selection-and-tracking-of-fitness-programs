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
import java.time.LocalDate;
import java.util.List;
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
        Mockito.when(searcherDao.findExerciseByProgramId(1L)).thenReturn(scheduleList);
        List <ExerciseSchedule> actual = scheduleService.readExerciseByActiveProgramId(1L);
        List <ExerciseSchedule> expected = scheduleList;
        Assertions.assertEquals(expected, actual);
        Mockito.verify(searcherDao, Mockito.times(1)).findExerciseByProgramId(1L);
    }
    @Test
    void readExerciseByDate() {
        List<ExerciseSchedule> scheduleList = Stream
                .generate(() -> getSchedule())
                .limit(3)
                .collect(Collectors.toList());
        LocalDate currentDate = LocalDate.now();
        Mockito.when(searcherDao.findExerciseByDate(currentDate)).thenReturn(scheduleList);
        List <ExerciseSchedule> actual = scheduleService.readExerciseByDate(currentDate);
        List <ExerciseSchedule> expected = scheduleList;
        Assertions.assertEquals(expected, actual);
        Mockito.verify(searcherDao, Mockito.times(1)).findExerciseByDate(currentDate);
    }

    private ExerciseSchedule getSchedule(){
        ExerciseSchedule schedule = new ExerciseSchedule();
        schedule.setId(1);
        schedule.setExerciseDate(LocalDate.of(2021, 10,18));
        schedule.setComplited(true);
        return schedule;
    }
}