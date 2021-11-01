package com.makul.fitness.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.makul.fitness.dto.ActiveProgramDto;
import com.makul.fitness.dto.ReviewDto;
import com.makul.fitness.exceptions.ActiveProgramIsPresentException;
import com.makul.fitness.exceptions.BookmarkIsPresentException;
import com.makul.fitness.exceptions.ReviewIsPresentException;
import com.makul.fitness.exceptions.ScheduleIsPresentException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@SqlGroup({
        @Sql("classpath:data-test.sql"),
        @Sql(scripts = "classpath:clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class ClientBusinessControllerTestIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @SneakyThrows
    void addBookmark_whenAdd_thenStatus200andBookmarkReturned(){
        mockMvc.perform(get("/user/{userId}/bookmark/{fitnessId}",2,1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.fitnessProgram").isNotEmpty());
    }

    @Test
    @SneakyThrows
    void addBookmark_whenAddExistingBookmark_thenStatus400andExceptionThrown(){
        mockMvc.perform(
                        get("/user/{userId}/bookmark/{fitnessId}", 1,1))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BookmarkIsPresentException))
                .andExpect(result -> assertEquals("Такая закладка уже есть у Вас.",
                        result.getResolvedException().getMessage()));
    }

    @Test
    @SneakyThrows
    void showBookmarks_whenGetExistingBookmarkList_thenStatus200andBookmarkListReturned(){
        mockMvc.perform(get("/user/{userId}/bookmarks", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", isA(ArrayList.class)))
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$[*].id", containsInAnyOrder(1,2)))
                .andExpect(jsonPath("$[*].fitnessProgram").isNotEmpty());
    }

    @Test
    @SneakyThrows
    void addActiveProgram_whenAdd_thenStatus200andActiveProgramReturned(){
        mockMvc.perform(get("/user/{userId}/program/active/{programId}",2,1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.fitnessProgram").isNotEmpty())
                .andExpect(jsonPath("$.scheduleList").isEmpty())
                .andExpect(jsonPath("$.days").isEmpty());
    }

    @Test
    @SneakyThrows
    void addActiveProgram_whenUserHaveActiveProgramNotComplited_thenStatus400andExceptionThrown(){
        mockMvc.perform(
                        get("/user/{userId}/program/active/{programId}", 1,5))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ActiveProgramIsPresentException))
                .andExpect(result -> assertEquals("У Вас есть незавершенные активные программы",
                        result.getResolvedException().getMessage()));
    }

    @Test
    @SneakyThrows
    void createScheduleList_whenCreate_thenStatus200andActiveProgramReturned(){
        mockMvc.perform(put("/program/fitness/schedule")
                        .content(objectMapper.writeValueAsString(getActiveProgramDto()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.scheduleList").isNotEmpty())
                .andExpect(jsonPath("$.scheduleList",isA(ArrayList.class)))
                .andExpect(jsonPath("$.scheduleList", hasSize(10)));
    }

    @Test
    @SneakyThrows
    void createScheduleList_whenUserHaveActiveProgramScheduleList_thenStatus400andExceptionThrown(){
        ActiveProgramDto activeProgramDto = getActiveProgramDto();
        activeProgramDto.setId(5);
        mockMvc.perform(put("/program/fitness/schedule")
                        .content(objectMapper.writeValueAsString(activeProgramDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ScheduleIsPresentException))
                .andExpect(result -> assertEquals("Расписание для данной программы уже составлено!",
                        result.getResolvedException().getMessage()));
    }

    @Test
    @SneakyThrows
    void addReview_whenAdd_thenStatus200andFitnessProgramReturned(){
        mockMvc.perform(post("/user/program/fitness/{fitnessProgramId}/review",1)
                        .content(objectMapper.writeValueAsString(getReviewDto()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.text").value("NewText"))
                .andExpect(jsonPath("$.authorId").value(2));
    }

    @Test
    @SneakyThrows
    void addReview_whenAddExistingReview_thenStatus400andExceptionThrown(){
        ReviewDto reviewDto = getReviewDto();
        reviewDto.setAuthorId(1);
        mockMvc.perform(post("/user/program/fitness/{fitnessProgramId}/review",1)
                        .content(objectMapper.writeValueAsString(reviewDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ReviewIsPresentException))
                .andExpect(result -> assertEquals("Вы уже оставили отзыв по данной программе",
                        result.getResolvedException().getMessage()));
    }

    @Test
    @SneakyThrows
    void updateSchedule_whenUpdate_thenStatus200andUpdatedScheduleReturns(){
        mockMvc.perform(get("/schedule/exercise/{id}",4))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.programShortName").value("Test4Name"));
    }

    private ActiveProgramDto getActiveProgramDto(){
        ActiveProgramDto activeProgramDto = new ActiveProgramDto();
        activeProgramDto.setDays("Monday;Friday;");
        activeProgramDto.setId(6);
        return activeProgramDto;
    }

    private ReviewDto getReviewDto(){
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setAuthorId(2);
        reviewDto.setText("NewText");
        return reviewDto;
    }
}