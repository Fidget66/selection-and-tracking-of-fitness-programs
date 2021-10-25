package com.makul.fitness.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.makul.fitness.exceptions.IncorrectDataException;
import com.makul.fitness.exceptions.NoEntityException;
import com.makul.fitness.model.Review;
import com.makul.fitness.service.api.ReviewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ReviewControllerTestIT {

    @Autowired
    private ReviewService reviewService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void updateReview_whenUpdate_thenStatus200andUpdatedReturns() throws Exception  {
        mockMvc.perform(put("/review")
                        .content(objectMapper.writeValueAsString(getReview()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.text").value("Test review 36"))
                .andExpect(jsonPath("$.authorId").value(1));
    }

    @Test
    void readReview_whenGetExistingReview_thenStatus200andReviewReturned() throws Exception {
        mockMvc.perform(get("/review/{id}", 2))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.text").value("Test review 2"))
                .andExpect(jsonPath("$.authorId").value(1));
    }

    @Test
    void readReview_whenGetNotExistingReview_thenStatus400andExceptionThrown() throws Exception {
        mockMvc.perform(
                        get("/review/{id}", 90)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NoEntityException))
                .andExpect(result -> assertEquals("Такой записи для Review в базе данных не существует",
                        result.getResolvedException().getMessage()));

        mockMvc.perform(get("/review/{id}", -1))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof IncorrectDataException))
                .andExpect(result -> assertEquals("Введены некорректные данные для Review id",
                        result.getResolvedException().getMessage()));
    }

    private Review getReview(){
        Review review = new Review();
        review.setId(1);
        review.setAuthorId(1);
        review.setText("Test review 36");
        return review;
    }
}