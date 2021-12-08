package com.makul.fitness.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.makul.fitness.dto.ActiveProgramDto;
import com.makul.fitness.dto.ReviewDto;
import com.makul.fitness.exceptions.BusinessException;
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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
        activeProgramDto.setId(UUID.fromString("00000000-0000-0000-0000-000000000017"));
        mockMvc.perform(put("/program/fitness/schedule")
                        .content(objectMapper.writeValueAsString(activeProgramDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BusinessException))
                .andExpect(result -> assertEquals("Расписание для данной программы уже составлено!",
                        result.getResolvedException().getMessage()));
    }

    @Test
    @SneakyThrows
    void updateSchedule_whenUpdate_thenStatus200andUpdatedScheduleReturns(){
        mockMvc.perform(get("/schedule/exercise/{id}","00000000-0000-0000-0000-000000000022"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.programShortName").value("Test4Name"));
    }

    private ActiveProgramDto getActiveProgramDto(){
        ActiveProgramDto activeProgramDto = new ActiveProgramDto();
        activeProgramDto.setDays("Monday;Friday;");
        activeProgramDto.setId(UUID.fromString("00000000-0000-0000-0000-000000000018"));
        return activeProgramDto;
    }

    private ReviewDto getReviewDto(){
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setAuthorId(UUID.fromString("00000000-0000-0000-0000-000000000002"));
        reviewDto.setText("NewText");
        return reviewDto;
    }
}