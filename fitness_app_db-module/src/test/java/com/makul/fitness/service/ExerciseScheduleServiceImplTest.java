package com.makul.fitness.service;

import com.makul.fitness.dao.ExerciseScheduleDao;
import com.makul.fitness.exceptions.IncorrectDataException;
import com.makul.fitness.exceptions.NoEntityException;
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
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
class ExerciseScheduleServiceImplTest {

    @Mock
    private ExerciseScheduleDao scheduleDao;
    @InjectMocks
    private ExerciseScheduleServiceImpl scheduleService;

    @Test
    void whenCreate_returnListExerciseSchedule() {
        List<ExerciseSchedule> exerciseScheduleList = Stream
                .generate(() -> getSchedule())
                .limit(3)
                .collect(Collectors.toList());
        Mockito.when(scheduleDao.saveAll(exerciseScheduleList)).thenReturn(exerciseScheduleList);
        List <ExerciseSchedule> actual = scheduleService.createAll(exerciseScheduleList);
        List <ExerciseSchedule> expected = exerciseScheduleList;
        Assertions.assertEquals(expected, actual);
        Mockito.verify(scheduleDao, Mockito.times(1)).saveAll(exerciseScheduleList);
    }

    @Test
    void whenUpdate_returnExerciseSchedule() {
        ExerciseSchedule exerciseSchedule = getSchedule();
        Mockito.when(scheduleDao.findById(1L)).thenReturn(Optional.ofNullable(exerciseSchedule));
        Mockito.when(scheduleDao.save(exerciseSchedule)).thenReturn(exerciseSchedule);
        ExerciseSchedule actual = scheduleService.update(1L);
        ExerciseSchedule expected = exerciseSchedule;
        Assertions.assertEquals(expected,actual);
        Mockito.verify(scheduleDao, Mockito.times(1)).findById(1L);
        Mockito.verify(scheduleDao,Mockito.times(1)).save(exerciseSchedule);
    }

    @Test
    void whenRead_returnExerciseSchedule() {
        ExerciseSchedule exerciseSchedule = getSchedule();
        Mockito.when(scheduleDao.findById(1L)).thenReturn(Optional.ofNullable(exerciseSchedule));
        ExerciseSchedule actual = scheduleService.read(1L);
        ExerciseSchedule expected = exerciseSchedule;
        Assertions.assertEquals(expected, actual);
        Mockito.verify(scheduleDao, Mockito.times(1)).findById(1L);
    }

    @Test
    void whenRead_throwException() {
        ExerciseSchedule exerciseSchedule = getSchedule();
        Mockito.when(scheduleDao.findById(4L)).thenReturn(Optional.empty());
        NoEntityException noEntityException = Assertions.assertThrows(NoEntityException.class,
                ()->scheduleService.read(4L));
        Assertions.assertEquals(noEntityException.getMessage(),
                "Такой записи для Exercise Schedule в базе данных не существует");
        Mockito.verify(scheduleDao, Mockito.times(1)).findById(4L);

        IncorrectDataException incorrectDataException = Assertions.assertThrows(IncorrectDataException.class,
                ()->scheduleService.read(-1L));
        Assertions.assertEquals(incorrectDataException.getMessage(),
                "Введены некорректные данные для Exercise Schedule id");
        Mockito.verify(scheduleDao, Mockito.times(0)).findById(-1L);
    }

    @Test
    void readExerciseByDate() {
        List <ExerciseSchedule> scheduleList = Stream
                .generate(() -> getSchedule())
                .limit(3)
                .collect(Collectors.toList());
        LocalDate currentDate = LocalDate.now();
        Mockito.when(scheduleDao.findExerciseByDate(currentDate)).thenReturn(scheduleList);
        List <ExerciseSchedule> actual = scheduleService.readExerciseByDate(currentDate);
        List <ExerciseSchedule> expected = scheduleList;
        Assertions.assertEquals(expected, actual);
        Mockito.verify(scheduleDao, Mockito.times(1)).findExerciseByDate(currentDate);
    }

    private ExerciseSchedule getSchedule(){
        ExerciseSchedule schedule = new ExerciseSchedule();
        schedule.setId(1);
        schedule.setExerciseDate(LocalDate.of(2021, 10,18));
        schedule.setComplited(true);
        return schedule;
    }
}