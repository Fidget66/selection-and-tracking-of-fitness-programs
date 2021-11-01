package com.makul.fitness.service;

import com.makul.fitness.exceptions.ActiveProgramIsPresentException;
import com.makul.fitness.exceptions.BookmarkIsPresentException;
import com.makul.fitness.exceptions.ReviewIsPresentException;
import com.makul.fitness.exceptions.ScheduleIsPresentException;
import com.makul.fitness.model.*;
import com.makul.fitness.service.api.ClientBusinessService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@SqlGroup({
        @Sql("classpath:data-test.sql"),
        @Sql(scripts = "classpath:clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
public class ClientBusinessServiceImplTestIT {

    @Autowired
    private ClientBusinessService clientBusinessService;

    @Test
    @Transactional
    void whenAddBookmark_thenReturnBookmark() {
        Bookmark bookmark = clientBusinessService.addBookmark(2,2);
        assertNotNull(bookmark);
        assertNotNull(bookmark.getId());
        assertEquals(2, bookmark.getFitnessProgram().getId());
        assertEquals(2, bookmark.getUser().getId());
    }

    @Test
    void whenAddPresentBookmark_thenThrowException() {
        BookmarkIsPresentException bookmarkIsPresentException =
                Assertions.assertThrows(BookmarkIsPresentException.class,
                        ()-> clientBusinessService.addBookmark(1,2));
        Assertions.assertEquals(bookmarkIsPresentException.getMessage(),
                "Такая закладка уже есть у Вас.");
    }

    @Test
    @Transactional
    void viewBookmarks_thenReturnBookmarkList() {
        List<Bookmark> bookmarks = clientBusinessService.viewBookmarks(1);
        assertNotNull(bookmarks);
        assertEquals(2, bookmarks.size());
        for (Bookmark bookmark: bookmarks) {
            assertNotNull(bookmark.getId());
            assertNotNull(bookmark.getFitnessProgram());
        }
    }

    @Test
    void addActiveProgram_thenReturnActiveProgram(){
        ActiveProgram activeProgram = clientBusinessService.addActiveProgram(2,2);
        assertNotNull(activeProgram);
        assertNotNull(activeProgram.getId());
        assertEquals("TestProgram2", activeProgram.getFitnessProgram().getShortName());
    }

    @Test
    void addActiveProgram_thenThrowException(){
        ActiveProgramIsPresentException activeProgramIsPresentException =
                Assertions.assertThrows(ActiveProgramIsPresentException.class,
                        ()-> clientBusinessService.addActiveProgram(1,2));
        Assertions.assertEquals(activeProgramIsPresentException.getMessage(),
                "У Вас есть незавершенные активные программы");
    }

    @Test
    @Transactional
    void createSchedule_thenReturnActiveProgramWithScheduleList(){
        ActiveProgram inputActiveProgram = getFilledActiveProgram();
        ActiveProgram activeProgram = clientBusinessService.createSchedule(inputActiveProgram);
        assertNotNull(activeProgram.getScheduleList());
        assertEquals(10, activeProgram.getScheduleList().size());
    }

    @Test
    void createSchedule_thenThrowException(){
        ActiveProgram inputActiveProgram = getFilledActiveProgram();
        inputActiveProgram.setId(1);
        ScheduleIsPresentException scheduleIsPresentException =
                Assertions.assertThrows(ScheduleIsPresentException.class,
                        ()-> clientBusinessService.createSchedule(inputActiveProgram));
        Assertions.assertEquals(scheduleIsPresentException.getMessage(),
                "Расписание для данной программы уже составлено!");
    }

    @Test
    void addReview_thenReturnReview(){
        Review review = getReview();
        Review expected = clientBusinessService.addReview(4,review);
        assertNotNull(expected);
        assertEquals("TestName",review.getAuthorName());
        assertEquals("TestProgram4", expected.getFitnessProgram().getShortName());
    }

    @Test
    void addReview_thenThrowException(){
        Review review = getReview();
        ReviewIsPresentException reviewIsPresentException =
                Assertions.assertThrows(ReviewIsPresentException.class,
                        ()-> clientBusinessService.addReview(2, review));
        Assertions.assertEquals(reviewIsPresentException.getMessage(),
                "Вы уже оставили отзыв по данной программе");
    }

    @Test
    void updateExercise_thenReturnExercise(){
       ExerciseSchedule exerciseSchedule = clientBusinessService.updateExercise(1);
       assertNotNull(exerciseSchedule);
       assertTrue(exerciseSchedule.isComplited());
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
