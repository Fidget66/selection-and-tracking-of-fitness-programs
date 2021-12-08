package com.makul.fitness.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.makul.fitness.dto.FitnessProgramDto;
import com.makul.fitness.exceptions.NoEntityException;
import lombok.SneakyThrows;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @SneakyThrows
    void readFitnessProgram_whenGetExistingFitnessProgram_thenStatus200andProgramReturned(){
        mockMvc.perform(get("/program/fitness/{id}", "00000000-000-0000-0000-000000000007"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.shortName").value("TestProgram1"))
                .andExpect(jsonPath("$.description").value("Description of TestProgram1"));

    }

    @Test
    @SneakyThrows
    void readProgram_whenGetNotExistingActiveProgram_thenStatus400andExceptionThrown(){
        mockMvc.perform(
                        get("/program/fitness/{id}", "00000000-000-0000-0000-000000000098")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NoEntityException))
                .andExpect(result -> assertEquals("Такой записи для Fitness Program в базе данных не существует",
                        result.getResolvedException().getMessage()));
    }

    @Test
    @SneakyThrows
    void addFitnessProgram_whenAdd_thenStatus200andFitnessProgramReturned(){
        mockMvc.perform(post("/category/{categoryId}/program/fitness","00000000-000-0000-0000-000000000005")
                        .content(objectMapper.writeValueAsString(getFitnessProgramDto()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.shortName").value("TestProgram"))
                .andExpect(jsonPath("$.description").value("TestDescription"))
                .andExpect(jsonPath("$.duration").value(16))
                .andExpect(jsonPath("$.ageRestriction").value(16))
                .andExpect(jsonPath("$.exercisePerWeek").value(7))
                .andExpect(jsonPath("$.sexRestriction").value("f"))
                .andExpect(jsonPath("$.weightRestriction").value(160));
    }

    private FitnessProgramDto getFitnessProgramDto(){
        FitnessProgramDto fitnessProgramDto = new FitnessProgramDto();
        fitnessProgramDto.setShortName("TestProgram");
        fitnessProgramDto.setDescription("TestDescription");
        fitnessProgramDto.setDuration(16);
        fitnessProgramDto.setAgeRestriction(16);
        fitnessProgramDto.setExercisePerWeek(7);
        fitnessProgramDto.setSexRestriction("f");
        fitnessProgramDto.setWeightRestriction(160);
        return fitnessProgramDto;
    }
}
