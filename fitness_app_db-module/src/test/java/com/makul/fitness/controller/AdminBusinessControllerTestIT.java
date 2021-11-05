package com.makul.fitness.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.makul.fitness.dto.FitnessProgramDto;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@SqlGroup({
        @Sql("classpath:data-test.sql"),
        @Sql(scripts = "classpath:clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class AdminBusinessControllerTestIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @SneakyThrows
    void addFitnessProgram_whenAdd_thenStatus200andFitnessProgramReturned(){
        mockMvc.perform(post("/category/{categoryId}/program/fitness",1)
                        .content(objectMapper.writeValueAsString(getFitnessProgramDto()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
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