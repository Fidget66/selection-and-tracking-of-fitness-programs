package com.makul.fitness.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.makul.fitness.dto.FiltredDto;
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
import java.util.UUID;

import static org.hamcrest.Matchers.*;
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
class FitnessProgramSearcherControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @SneakyThrows
    void readFitnessProgramList_whenGetExistingFitnessProgramList_thenStatus200andProgramListReturned(){
        mockMvc.perform(get("/category/{id}/program/fitness","00000000-000-0000-0000-000000000005")
                .param("page","0")
                .param("size","20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", isA(ArrayList.class)))
                .andExpect(jsonPath("$.content", hasSize(3)))
                .andExpect(jsonPath("$.content[*].shortName", containsInAnyOrder("TestProgram1","TestProgram2",
                        "TestProgram3")));
    }

    @Test
    @SneakyThrows
    void readFitnessProgramListWithRestrictions_whenGetExistingFitnessProgramList_thenStatus200andProgramListReturned(){
        mockMvc.perform(post("/user/program/fitness")
                        .param("page","0")
                        .param("size","20")
                .content(objectMapper.writeValueAsString(getFiltredDto()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", isA(ArrayList.class)))
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[*].shortName", containsInAnyOrder("TestProgram1")));
    }

    private FiltredDto getFiltredDto(){
        FiltredDto filtredDto = new FiltredDto();
        filtredDto.setUserId(UUID.fromString("00000000-000-0000-0000-000000000001"));
        filtredDto.setDuration(50);
        filtredDto.setCategoryId(UUID.fromString("00000000-000-0000-0000-000000000005"));
        return filtredDto;
    }
}