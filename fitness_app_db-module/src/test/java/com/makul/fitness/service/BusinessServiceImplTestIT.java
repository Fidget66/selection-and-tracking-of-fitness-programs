package com.makul.fitness.service;

import com.makul.fitness.exceptions.ActiveProgramIsPresentException;
import com.makul.fitness.exceptions.BookmarkIsPresentException;
import com.makul.fitness.exceptions.ReviewIsPresentException;
import com.makul.fitness.exceptions.ScheduleIsPresentException;
import com.makul.fitness.model.*;
import com.makul.fitness.service.api.BusinessService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@SqlGroup({
        @Sql("classpath:data-test.sql"),
        @Sql(scripts = "classpath:clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
public class BusinessServiceImplTestIT {

    @Autowired
    private BusinessService businessService;

    @Test
    @Transactional
    void whenAddBookmark_thenReturnBookmark() {
        Bookmark bookmark = businessService.addBookmark(2,2);
        assertNotNull(bookmark);
        assertNotNull(bookmark.getId());
        assertEquals(2, bookmark.getFitnessProgram().getId());
        assertEquals(2, bookmark.getUser().getId());
    }

    @Test
    void whenAddPresentBookmark_thenThrowException() {
        BookmarkIsPresentException bookmarkIsPresentException =
                Assertions.assertThrows(BookmarkIsPresentException.class,
                        ()->businessService.addBookmark(1,2));
        Assertions.assertEquals(bookmarkIsPresentException.getMessage(),
                "Такая закладка уже есть у Вас.");
    }

    @Test
    @Transactional
    void viewBookmarks_thenReturnBookmarkList() {
        List<Bookmark> bookmarks = businessService.viewBookmarks(1);
        assertNotNull(bookmarks);
        assertEquals(2, bookmarks.size());
        for (Bookmark bookmark: bookmarks) {
            assertNotNull(bookmark.getId());
            assertNotNull(bookmark.getFitnessProgram());
        }
    }

    @Test
    void addActiveProgram_thenReturnActiveProgram(){
        ActiveProgram activeProgram = businessService.addActiveProgram(2,2);
        assertNotNull(activeProgram);
        assertNotNull(activeProgram.getId());
        assertEquals("TestProgram2", activeProgram.getFitnessProgram().getShortName());
    }

    @Test
    void addActiveProgram_thenThrowException(){
        ActiveProgramIsPresentException activeProgramIsPresentException =
                Assertions.assertThrows(ActiveProgramIsPresentException.class,
                        ()->businessService.addActiveProgram(1,2));
        Assertions.assertEquals(activeProgramIsPresentException.getMessage(),
                "У Вас есть незавершенные активные программы");
    }

    @Test
    void addFitnessProgram_thenReturnFitnessProgram(){
        FitnessProgram fitnessProgram = businessService.addFitnessProgram(1,getFitnessProgram());
        assertNotNull(fitnessProgram);
        assertNotNull(fitnessProgram.getId());
        assertEquals("Test", fitnessProgram.getShortName());
        assertEquals("Test description",fitnessProgram.getDescription());
        assertEquals(66, fitnessProgram.getWeightRestriction());
    }

    @Test
    @Transactional
    void createSchedule_thenReturnActiveProgramWithScheduleList(){
        ActiveProgram inputActiveProgram = getFilledActiveProgram();
        ActiveProgram activeProgram = businessService.createSchedule(inputActiveProgram);
        assertNotNull(activeProgram.getScheduleList());
        assertEquals(10, activeProgram.getScheduleList().size());
    }

    @Test
    void createSchedule_thenThrowException(){
        ActiveProgram inputActiveProgram = getFilledActiveProgram();
        inputActiveProgram.setId(1);
        ScheduleIsPresentException scheduleIsPresentException =
                Assertions.assertThrows(ScheduleIsPresentException.class,
                        ()->businessService.createSchedule(inputActiveProgram));
        Assertions.assertEquals(scheduleIsPresentException.getMessage(),
                "Расписание для данной программы уже составлено!");
    }

    @Test
    void addReview_thenReturnReview(){
        Review review = getReview();
        Review expected = businessService.addReview(4,review);
        assertNotNull(expected);
        assertEquals("TestName",review.getAuthorName());
        assertEquals("TestProgram4", expected.getFitnessProgram().getShortName());
    }

    @Test
    void addReview_thenThrowException(){
        Review review = getReview();
        ReviewIsPresentException reviewIsPresentException =
                Assertions.assertThrows(ReviewIsPresentException.class,
                        ()->businessService.addReview(2, review));
        Assertions.assertEquals(reviewIsPresentException.getMessage(),
                "Вы уже оставили отзыв по данной программе");
    }

    @Test
    void updateExercise_thenReturnExercise(){
       ExerciseSchedule exerciseSchedule = businessService.updateExercise(1);
       assertNotNull(exerciseSchedule);
       assertTrue(exerciseSchedule.isComplited());
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

    private ActiveProgram getFilledActiveProgram(){
        ActiveProgram activeProgram = new ActiveProgram();
        activeProgram.setComplited(false);
        activeProgram.setDays("MONDAY;SUNDAY");
        activeProgram.setId(4L);
        return activeProgram;
    }

    private Review getReview(){
        Review review = new Review();
        review.setAuthorId(1);
        return review;
    }
}
