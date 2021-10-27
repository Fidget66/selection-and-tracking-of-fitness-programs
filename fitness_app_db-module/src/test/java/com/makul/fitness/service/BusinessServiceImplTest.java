package com.makul.fitness.service;

import com.makul.fitness.exceptions.ActiveProgramIsPresentException;
import com.makul.fitness.exceptions.ScheduleIsPresentException;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
class BusinessServiceImplTest {
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
    private CategoryOfFitnessProgramService categoryService;
    @Mock
    private ReviewService reviewService;
    @InjectMocks
    private BusinessServiceImpl businessService;


    @Test
    void whenAddBookmark_thenOk() {
        Users user = getFilledUser();
        FitnessProgram fitnessProgram = getFitnessProgram();
        Bookmark bookmark = getBookmark();
        bookmark.setUser(user);
        bookmark.setFitnessProgram(fitnessProgram);
        Mockito.when(usersService.read(1L)).thenReturn(user);
        Mockito.when(fitnessProgramService.read(2L)).thenReturn(fitnessProgram);
        businessService.addBookmark(1, 2);
        Mockito.verify(usersService, Mockito.times(1)).read(1L);
        Mockito.verify(fitnessProgramService, Mockito.times(1)).read(2L);
        Mockito.verify(bookmarkService, Mockito.times(1)).create(bookmark);
    }

    @Test
    void whenAddPresentBookmark_thenDidNotAdd() {
        Users user = getFilledUser();
        FitnessProgram fitnessProgram = getFitnessProgram();
        Bookmark bookmark = getBookmark();
        fitnessProgram.setId(3);
        bookmark.setFitnessProgram(fitnessProgram);
        bookmark.setId(1);
        user.setBookmarks(List.of(bookmark));
        Mockito.when(usersService.read(1L)).thenReturn(user);
        Mockito.when(fitnessProgramService.read(2L)).thenReturn(fitnessProgram);
        businessService.addBookmark(1, 2);
        Mockito.verify(usersService, Mockito.times(1)).read(1L);
        Mockito.verify(fitnessProgramService, Mockito.times(1)).read(2L);
        Mockito.verify(bookmarkService, Mockito.times(0)).create(bookmark);
    }

    @Test
    void whenAddActiveProgram_thenOk() {
        Users user = getFilledUser();
        FitnessProgram fitnessProgram = getFitnessProgram();
        ActiveProgram activeProgram = getActiveProgram();
        activeProgram.setUser(user);
        activeProgram.setFitnessProgram(fitnessProgram);
        Mockito.when(usersService.read(1L)).thenReturn(user);
        Mockito.when(fitnessProgramService.read(2L)).thenReturn(fitnessProgram);
        Mockito.when(activeProgramService.create(activeProgram)).thenReturn(activeProgram);
        ActiveProgram actual = businessService.addActiveProgram(1, 2);
        ActiveProgram expected = activeProgram;
        Assertions.assertEquals(expected, actual);
        Mockito.verify(usersService, Mockito.times(1)).read(1L);
        Mockito.verify(fitnessProgramService, Mockito.times(1)).read(2L);
        Mockito.verify(activeProgramService, Mockito.times(1)).create(activeProgram);
    }

    @Test
    void whenAddActiveProgram_throwException() {
        Users user = getFilledUser();
        FitnessProgram fitnessProgram = getFitnessProgram();
        ActiveProgram activeProgram = getActiveProgram();
        user.setActivePrograms(List.of(activeProgram));
        activeProgram.setUser(user);
        activeProgram.setFitnessProgram(fitnessProgram);
        Mockito.when(usersService.read(2L)).thenReturn(user);
        Mockito.when(fitnessProgramService.read(3L)).thenReturn(fitnessProgram);
        ActiveProgramIsPresentException programIsPresentException =
                Assertions.assertThrows(ActiveProgramIsPresentException.class,
                        ()->businessService.addActiveProgram(2,3));
        Assertions.assertEquals(programIsPresentException.getMessage(),
                "У Вас есть незавершенные активные программы");
        Mockito.verify(usersService, Mockito.times(1)).read(2L);
        Mockito.verify(fitnessProgramService, Mockito.times(1)).read(3L);
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

        Mockito.when(activeProgramService.read(1L)).thenReturn(newActiveProgram);
        Mockito.when(exerciseScheduleService.createAll(Mockito.anyList())).thenReturn(scheduleList);
        Mockito.when(activeProgramService.update(newActiveProgram)).thenReturn(expected);

        ActiveProgram actual = businessService.createSchedule(newActiveProgram);
        Assertions.assertEquals(expected, actual);

        Mockito.verify(activeProgramService, Mockito.times(1)).read(1L);
        Mockito.verify(exerciseScheduleService, Mockito.times(1)).createAll(Mockito.anyList());
        Mockito.verify(activeProgramService, Mockito.times(1)).update(newActiveProgram);
    }

    @Test
    void whenCreateScheduleList_throwException() {
        ActiveProgram activeProgram = getFilledActiveProgram();
        activeProgram.setScheduleList(List.of(getExerciseSchedule()));
        Mockito.when(activeProgramService.read(1L)).thenReturn(activeProgram);
        ScheduleIsPresentException scheduleIsPresentException = Assertions.assertThrows(ScheduleIsPresentException.class,
                ()->businessService.createSchedule(activeProgram));
        Assertions.assertEquals(scheduleIsPresentException.getMessage(),
                "Расписание для данной программы уже составлено!");
        Mockito.verify(activeProgramService, Mockito.times(1)).read(1L);
        Mockito.verify(exerciseScheduleService, Mockito.times(0)).createAll(Mockito.anyList());
        Mockito.verify(activeProgramService, Mockito.times(0)).update(Mockito.any());
    }

    @Test
    void whenAddReview_thenOk() {
        FitnessProgram fitnessProgram = getFitnessProgram();
        Review review = getReview();
        Mockito.when(fitnessProgramService.read(1L)).thenReturn(fitnessProgram);
        Mockito.when(usersService.read(1L)).thenReturn(getFilledUser());
        Mockito.when(reviewService.create(review)).thenReturn(review);
        Review actual = businessService.addReview(1,review);
        Review expected = review;
        Assertions.assertEquals(expected, actual);
        Mockito.verify(fitnessProgramService, Mockito.times(1)).read(1L);
        Mockito.verify(reviewService, Mockito.times(1)).create(review);
    }

    @Test
    void whenAddFitnessProgram_thenOk() {
        FitnessProgram fitnessProgram = getFitnessProgram();
        CategoryOfFitnessProgram category = getCategory();
        Mockito.when(categoryService.read(3L)).thenReturn(category);
        Mockito.when(fitnessProgramService.create(fitnessProgram)).thenReturn(fitnessProgram);
        FitnessProgram actual = businessService.addFitnessProgram(3, fitnessProgram);
        FitnessProgram expected = fitnessProgram;
        Assertions.assertEquals(expected, actual);
        Mockito.verify(categoryService, Mockito.times(1)).read(3L);
        Mockito.verify(fitnessProgramService, Mockito.times(1)).create(fitnessProgram);
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
        activeProgram.setId(1L);
        return activeProgram;
    }

    private ActiveProgram getActiveProgram(){
        ActiveProgram activeProgram = new ActiveProgram();
        activeProgram.setComplited(false);
        return activeProgram;
    }

    private ExerciseSchedule getExerciseSchedule(){//ActiveProgram activeProgram){
        ExerciseSchedule schedule = new ExerciseSchedule();
        LocalDate currentDate = LocalDate.now();
        schedule.setExerciseDate(currentDate);
        return schedule;
    }

    private Review getReview(){
        Review review = new Review();
        review.setAuthorId(1);
        return review;
    }

    private CategoryOfFitnessProgram getCategory(){
        return new CategoryOfFitnessProgram();
    }

}