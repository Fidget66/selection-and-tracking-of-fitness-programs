package com.makul.fitness.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.junit.jupiter.api.Assertions.*;
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
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.text").value("Test review 36"))
                .andExpect(jsonPath("$.authorId").value(1));
    }

    @Test
    @SneakyThrows
    void readReview_whenGetExistingReview_thenStatus200andReviewReturned(){
        mockMvc.perform(get("/review/{id}", 2))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.text").value("Test review 2"))
                .andExpect(jsonPath("$.authorId").value(1));
    }

    @Test
    @SneakyThrows
    void readReview_whenGetNotExistingReview_thenStatus400andExceptionThrown(){
        mockMvc.perform(
                        get("/review/{id}", 90)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NoEntityException))
                .andExpect(result -> assertEquals("Такой записи для Review в базе данных не существует",
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