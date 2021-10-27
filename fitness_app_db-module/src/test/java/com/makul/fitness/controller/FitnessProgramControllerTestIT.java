package com.makul.fitness.controller;

import com.makul.fitness.exceptions.IncorrectDataException;
import com.makul.fitness.exceptions.NoEntityException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@SqlGroup({
        @Sql("classpath:data-test.sql"),
        @Sql(scripts = "classpath:clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
public class FitnessProgramControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void readFitnessProgram_whenGetExistingFitnessProgram_thenStatus200andProgramReturned() throws Exception {
        mockMvc.perform(get("/program/fitness/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.shortName").value("TestProgram1"))
                .andExpect(jsonPath("$.description").value("Description of TestProgram1"));

    }

    @Test
    void readProgram_whenGetNotExistingActiveProgram_thenStatus400andExceptionThrown() throws Exception {
        mockMvc.perform(
                        get("/program/fitness/{id}", 90)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NoEntityException))
                .andExpect(result -> assertEquals("Такой записи для Fitness Program в базе данных не существует",
                        result.getResolvedException().getMessage()));

        mockMvc.perform(get("/program/fitness/{id}", -1))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof IncorrectDataException))
                .andExpect(result -> assertEquals("Введены некорректные данные для Fitness Program id",
                        result.getResolvedException().getMessage()));
    }
}
