package com.makul.fitness.service;

import com.makul.fitness.exceptions.BusinessException;
import com.makul.fitness.model.*;
import com.makul.fitness.service.api.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
class ClientBusinessServiceImplTest {
    @Mock
    private UsersService usersService;
    @Mock
    private BookmarkService bookmarkService;
    @Mock
    private FitnessProgramService fitnessProgramService;
    @Mock
    private ActiveProgramService activeProgramService;
    @Mock
    private ExerciseScheduleService exerciseScheduleService;
    @Mock
    private ReviewService reviewService;
    @InjectMocks
    private ClientBusinessServiceImpl businessService;

    @Test
    void whenAddBookmark_thenOk() {
        Users user = getFilledUser();
        FitnessProgram fitnessProgram = getFitnessProgram();
        Bookmark bookmark = getBookmark();
        bookmark.setUser(user);
        bookmark.setFitnessProgram(fitnessProgram);
        UUID uuid = getUUID();
        UUID uuid2 = getUUID();
        Mockito.when(usersService.read(uuid)).thenReturn(user);
        Mockito.when(fitnessProgramService.read(uuid2)).thenReturn(fitnessProgram);
        businessService.addBookmark(uuid, uuid2);
        Mockito.verify(usersService, Mockito.times(1)).read(uuid);
        Mockito.verify(fitnessProgramService, Mockito.times(1)).read(uuid2);
        Mockito.verify(bookmarkService, Mockito.times(1)).create(bookmark);
    }

    @Test
    void whenAddPresentBookmark_thenThrowException() {
        Users user = getFilledUser();
        FitnessProgram fitnessProgram = getFitnessProgram();
        Bookmark bookmark = getBookmark();
        UUID uuid = getUUID();
        UUID uuid2 = getUUID();
        fitnessProgram.setId(uuid2);
        bookmark.setFitnessProgram(fitnessProgram);
        user.setBookmarks(List.of(bookmark));
        Mockito.when(usersService.read(uuid)).thenReturn(user);
        BusinessException businessException =
                Assertions.assertThrows(BusinessException.class,
                        ()->businessService.addBookmark(uuid,uuid2));
        Assertions.assertEquals(businessException.getMessage(),
                "Такая закладка уже есть у Вас.");
        Mockito.verify(bookmarkService, Mockito.times(0)).create(bookmark);
    }

    @Test
    void whenAddActiveProgram_thenOk() {
        Users user = getFilledUser();
        FitnessProgram fitnessProgram = getFitnessProgram();
        ActiveProgram activeProgram = getActiveProgram();
        activeProgram.setUser(user);
        UUID uuid = getUUID();
        UUID uuid2 = getUUID();
        activeProgram.setFitnessProgram(fitnessProgram);
        Mockito.when(usersService.read(uuid)).thenReturn(user);
        Mockito.when(fitnessProgramService.read(uuid2)).thenReturn(fitnessProgram);
        Mockito.when(activeProgramService.create(activeProgram)).thenReturn(activeProgram);
        ActiveProgram actual = businessService.addActiveProgram(uuid, uuid2);
        ActiveProgram expected = activeProgram;
        Assertions.assertEquals(expected, actual);
        Mockito.verify(usersService, Mockito.times(1)).read(uuid);
        Mockito.verify(fitnessProgramService, Mockito.times(1)).read(uuid2);
        Mockito.verify(activeProgramService, Mockito.times(1)).create(activeProgram);
    }

    @Test
    void whenAddActiveProgram_throwException() {
        Users user = getFilledUser();
        FitnessProgram fitnessProgram = getFitnessProgram();
        ActiveProgram activeProgram = getActiveProgram();
        user.setActivePrograms(List.of(activeProgram));
        activeProgram.setUser(user);
        UUID uuid = getUUID();
        UUID uuid2 = getUUID();
        activeProgram.setFitnessProgram(fitnessProgram);
        Mockito.when(usersService.read(uuid)).thenReturn(user);
        Mockito.when(fitnessProgramService.read(uuid2)).thenReturn(fitnessProgram);
        BusinessException businessException =
                Assertions.assertThrows(BusinessException.class,
                        ()->businessService.addActiveProgram(uuid,uuid2));
        Assertions.assertEquals(businessException.getMessage(),
                "У Вас есть незавершенные активные программы");
        Mockito.verify(usersService, Mockito.times(1)).read(uuid);
        Mockito.verify(fitnessProgramService, Mockito.times(1)).read(uuid2);
        Mockito.verify(activeProgramService, Mockito.times(0)).create(activeProgram);
    }


    @Test
    void whenCreateScheduleList_thenOk() {
        ActiveProgram newActiveProgram = getFilledActiveProgram();
        ActiveProgram expected = getFilledActiveProgram();
        FitnessProgram fitnessProgram = getFitnessProgram();
        newActiveProgram.setFitnessProgram(fitnessProgram);
        expected.setFitnessProgram(fitnessProgram);
        List<ExerciseSchedule> scheduleList = Stream
                .generate(()->getExerciseSchedule())
                .limit(3)
                .collect(Collectors.toList());
        expected.setScheduleList(scheduleList);
        UUID uuid = expected.getId();
        Mockito.when(activeProgramService.read(uuid)).thenReturn(newActiveProgram);
        Mockito.when(exerciseScheduleService.createAll(Mockito.anyList())).thenReturn(scheduleList);
        Mockito.when(activeProgramService.update(newActiveProgram)).thenReturn(expected);

        ActiveProgram actual = businessService.createSchedule(newActiveProgram);
        Assertions.assertEquals(expected, actual);

        Mockito.verify(activeProgramService, Mockito.times(1)).read(uuid);
        Mockito.verify(exerciseScheduleService, Mockito.times(1)).createAll(Mockito.anyList());
        Mockito.verify(activeProgramService, Mockito.times(1)).update(newActiveProgram);
    }

    @Test
    void whenCreateScheduleList_throwException() {
        ActiveProgram activeProgram = getFilledActiveProgram();
        activeProgram.setScheduleList(List.of(getExerciseSchedule()));
        UUID uuid = activeProgram.getId();
        Mockito.when(activeProgramService.read(uuid)).thenReturn(activeProgram);
        BusinessException businessException = Assertions.assertThrows(BusinessException.class,
                ()->businessService.createSchedule(activeProgram));
        Assertions.assertEquals(businessException.getMessage(),
                "Расписание для данной программы уже составлено!");
        Mockito.verify(activeProgramService, Mockito.times(1)).read(uuid);
        Mockito.verify(exerciseScheduleService, Mockito.times(0)).createAll(Mockito.anyList());
        Mockito.verify(activeProgramService, Mockito.times(0)).create(Mockito.any());
    }

    @Test
    void whenAddReview_thenOk() {
        FitnessProgram fitnessProgram = getFitnessProgram();
        Review review = getReview();
        UUID uuid = UUID.fromString("00000000-0000-0000-0000-000000000001");
        Mockito.when(fitnessProgramService.read(uuid)).thenReturn(fitnessProgram);
        Mockito.when(usersService.read(uuid)).thenReturn(getFilledUser());
        Mockito.when(reviewService.create(review)).thenReturn(review);
        Review actual = businessService.addReview(uuid,review);
        Review expected = review;
        Assertions.assertEquals(expected, actual);
        Mockito.verify(fitnessProgramService, Mockito.times(1)).read(uuid);
        Mockito.verify(reviewService, Mockito.times(1)).create(review);
    }

    private Bookmark getBookmark(){
        return new Bookmark();
    }

    private FitnessProgram getFitnessProgram(){
        FitnessProgram fitnessProgram = new FitnessProgram();
        fitnessProgram.setShortName("Test");
        fitnessProgram.setDescription("Test description");
        fitnessProgram.setAgeRestriction(30);
        fitnessProgram.setExercisePerWeek(3);
        fitnessProgram.setWeightRestriction(66);
        fitnessProgram.setDuration(3);
        return fitnessProgram;
    }

    private Users getFilledUser(){
        Users user = new Users();
        user.setFirstName("Andy");
        user.setLastName("Popov");
        user.setSex("m");
        user.setEmail("Andy@email");
        user.setWeight(82);
        user.setDateOfBirth(LocalDate.of(1980,05,25));
        return user;
    }

    private ActiveProgram getFilledActiveProgram(){
        ActiveProgram activeProgram = new ActiveProgram();
        activeProgram.setComplited(false);
        activeProgram.setDays("MONDAY;SUNDAY");
        activeProgram.setId(UUID.fromString("00000000-000-0000-0000-000000000013"));
        return activeProgram;
    }

    private ActiveProgram getActiveProgram(){
        ActiveProgram activeProgram = new ActiveProgram();
        activeProgram.setComplited(false);
        return activeProgram;
    }

    private ExerciseSchedule getExerciseSchedule(){
        ExerciseSchedule schedule = new ExerciseSchedule();
        LocalDate currentDate = LocalDate.now();
        schedule.setExerciseDate(currentDate);
        return schedule;
    }

    private Review getReview(){
        Review review = new Review();
        review.setAuthorId(UUID.fromString("00000000-000-0000-0000-000000000001"));
        return review;
    }

    private UUID getUUID(){
        return UUID.randomUUID();
    }
}