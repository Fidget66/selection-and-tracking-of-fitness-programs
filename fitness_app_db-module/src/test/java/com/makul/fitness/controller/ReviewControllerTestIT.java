package com.makul.fitness.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.makul.fitness.dto.ReviewDto;
import com.makul.fitness.exceptions.BusinessException;
import com.makul.fitness.exceptions.NoEntityException;
import com.makul.fitness.model.Review;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

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
class ReviewControllerTestIT {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    void updateReview_whenUpdate_thenStatus200andUpdatedReviewReturns(){
        mockMvc.perform(put("/review")
                        .content(objectMapper.writeValueAsString(getReview()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.text").value("Test review 36"))
                .andExpect(jsonPath("$.authorId").value("00000000-0000-0000-0000-000000000001"));
    }

    @Test
    @SneakyThrows
    void readReview_whenGetExistingReview_thenStatus200andReviewReturned(){
        mockMvc.perform(get("/review/{id}", "00000000-0000-0000-0000-000000000025"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.text").value("Test review 2"))
                .andExpect(jsonPath("$.authorId").value("00000000-0000-0000-0000-000000000001"));
    }

    @Test
    @SneakyThrows
    void readReview_whenGetNotExistingReview_thenStatus400andExceptionThrown(){
        mockMvc.perform(
                        get("/review/{id}", "00000000-000-0000-0000-000000000077")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NoEntityException))
                .andExpect(result -> assertEquals("Такой записи для Review Id=" +
                                "00000000-0000-0000-0000-000000000077 в базе данных не существует",
                        result.getResolvedException().getMessage()));
    }

    @Test
    @SneakyThrows
    void addReview_whenAdd_thenStatus200andFitnessProgramReturned(){
        mockMvc.perform(post("/user/program/fitness/{fitnessProgramId}/review","00000000-000-0000-0000-000000000007")
                        .content(objectMapper.writeValueAsString(getReviewDto()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.text").value("NewText"))
                .andExpect(jsonPath("$.authorId").isNotEmpty());
    }

    @Test
    @SneakyThrows
    void addReview_whenAddExistingReview_thenStatus400andExceptionThrown(){
        ReviewDto reviewDto = getReviewDto();
        reviewDto.setAuthorId(UUID.fromString("00000000-0000-0000-0000-000000000001"));
        mockMvc.perform(post("/user/program/fitness/{fitnessProgramId}/review","00000000-0000-0000-0000-000000000007")
                        .content(objectMapper.writeValueAsString(reviewDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BusinessException))
                .andExpect(result -> assertEquals("Вы уже оставили отзыв по данной программе",
                        result.getResolvedException().getMessage()));
    }

    private Review getReview(){
        Review review = new Review();
        review.setId(UUID.fromString("00000000-0000-0000-0000-000000000024"));
        review.setAuthorId(UUID.fromString("00000000-0000-0000-0000-000000000001"));
        review.setText("Test review 36");
        return review;
    }

    private ReviewDto getReviewDto(){
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setAuthorId(UUID.fromString("00000000-0000-0000-0000-000000000002"));
        reviewDto.setText("NewText");
        return reviewDto;
    }
}