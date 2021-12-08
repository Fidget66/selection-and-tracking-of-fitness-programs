package com.makul.fitness.controller;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@SqlGroup({
        @Sql("classpath:data-test.sql"),
        @Sql(scripts = "classpath:clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class ExerciseScheduleSearcherControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    void getExercisesList_whenGetExistingExercisesList_thenStatus200andExercisesListReturned(){
        mockMvc.perform(get("/program/active/{id}/exercises", "00000000-000-0000-0000-000000000013")
                        .param("page","0")
                        .param("size","20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*").isNotEmpty())
                .andExpect(jsonPath("$.content", hasSize(4)))
                .andExpect(jsonPath("$.content[*].id", notNullValue()))
                .andExpect(jsonPath("$.content[*].exerciseDate").isNotEmpty());
    }
}