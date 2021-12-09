package com.makul.fitness.service;

import com.makul.fitness.exceptions.*;
import com.makul.fitness.model.ActiveProgram;
import com.makul.fitness.model.Bookmark;
import com.makul.fitness.model.ExerciseSchedule;
import com.makul.fitness.model.Review;
import com.makul.fitness.service.api.ClientBusinessService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@SqlGroup({
        @Sql("classpath:data-test.sql"),
        @Sql(scripts = "classpath:clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class ClientBusinessServiceImplTestIT {

    @Autowired
    private ClientBusinessService clientBusinessService;

    @Test
    @Transactional
    void whenAddBookmark_thenReturnBookmark() {
        Bookmark bookmark = clientBusinessService.addBookmark(UUID.fromString("00000000-0000-0000-0000-000000000002"),
                UUID.fromString("00000000-0000-0000-0000-000000000008"));
        assertNotNull(bookmark);
        assertEquals("00000000-0000-0000-0000-000000000008", bookmark.getFitnessProgram().getId().toString());
        assertEquals("00000000-0000-0000-0000-000000000002", bookmark.getUser().getId().toString());
    }

    @Test
    void whenAddPresentBookmark_thenThrowException() {
        BusinessException businessException =
                Assertions.assertThrows(BusinessException.class,
                        ()-> clientBusinessService.addBookmark(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                                UUID.fromString("00000000-0000-0000-0000-000000000008")));
        Assertions.assertEquals("Такая закладка уже есть у Вас.", businessException.getMessage());
    }

    @Test
    @Transactional
    void viewBookmarks_thenReturnBookmarkList() {
        Page <Bookmark> page = clientBusinessService.viewBookmarks(UUID.fromString("00000000-0000-0000-0000-000000000001"),0,20);
        List<Bookmark> bookmarks = page.getContent();
        assertNotNull(bookmarks);
        assertEquals(2, bookmarks.size());
        for (Bookmark bookmark: bookmarks) {
            assertNotNull(bookmark.getId());
            assertNotNull(bookmark.getFitnessProgram());
        }
    }

    @Test
    void addActiveProgram_thenReturnActiveProgram(){
        ActiveProgram activeProgram = clientBusinessService.addActiveProgram(UUID.fromString("00000000-0000-0000-0000-000000000002"),
                UUID.fromString("00000000-0000-0000-0000-000000000008"));
        assertNotNull(activeProgram);
        assertNotNull(activeProgram.getId());
        assertEquals("TestProgram2", activeProgram.getFitnessProgram().getShortName());
    }

    @Test
    void addActiveProgram_thenThrowException(){
        BusinessException businessException =
                Assertions.assertThrows(BusinessException.class,
                        ()-> clientBusinessService.addActiveProgram(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                                UUID.fromString("00000000-0000-0000-0000-000000000008")));
        Assertions.assertEquals("У Вас есть незавершенные активные программы",
                businessException.getMessage());
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
        inputActiveProgram.setId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        BusinessException businessException =
                Assertions.assertThrows(BusinessException.class,
                        ()-> clientBusinessService.createSchedule(inputActiveProgram));
        Assertions.assertEquals("Расписание для данной программы уже составлено!",
                businessException.getMessage());
    }

    @Test
    void addReview_thenReturnReview(){
        Review review = getReview();
        Review expected = clientBusinessService.addReview(UUID.fromString("00000000-0000-0000-0000-000000000010"),review);
        assertNotNull(expected);
        assertEquals("TestName",review.getAuthorName());
        assertEquals("TestProgram4", expected.getFitnessProgram().getShortName());
    }

    @Test
    void addReview_thenThrowException(){
        Review review = getReview();
        BusinessException businessException =
                Assertions.assertThrows(BusinessException.class,
                        ()-> clientBusinessService.addReview(UUID.fromString("00000000-0000-0000-0000-000000000008"), review));
        Assertions.assertEquals("Вы уже оставили отзыв по данной программе",
                businessException.getMessage());
    }

    @Test
    void updateExercise_thenReturnExercise(){
       ExerciseSchedule exerciseSchedule = clientBusinessService.updateExercise(UUID.fromString("00000000-0000-0000-0000-000000000019"));
       assertNotNull(exerciseSchedule);
       assertTrue(exerciseSchedule.isComplited());
    }

    private ActiveProgram getFilledActiveProgram(){
        ActiveProgram activeProgram = new ActiveProgram();
        activeProgram.setComplited(false);
        activeProgram.setDays("MONDAY;SUNDAY");
        activeProgram.setId(UUID.fromString("00000000-0000-0000-0000-000000000016"));
        return activeProgram;
    }

    private Review getReview(){
        Review review = new Review();
        review.setAuthorId(UUID.fromString("00000000-0000-0000-0000-000000000001"));
        return review;
    }
}
