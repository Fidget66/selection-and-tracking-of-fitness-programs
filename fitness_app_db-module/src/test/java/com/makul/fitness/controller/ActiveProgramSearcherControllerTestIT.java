package com.makul.fitness.controller;

import com.makul.fitness.exceptions.NoEntityException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
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
class ActiveProgramSearcherControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    void readAllComplitedPrograms_whenGetExistingActiveProgramList_thenStatus200andProgramListReturned(){
        mockMvc.perform(get("/user/{userId}/programs/active","00000000-0000-0000-0000-000000000001")
                .param("page","0")
                .param("size","20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*").isNotEmpty())
                .andExpect(jsonPath("$.content", hasSize(3)))
                .andExpect(jsonPath("$.content[*].id", notNullValue()))
                .andExpect(jsonPath("$.content[*].days", containsInAnyOrder("testDays1","testDays2",
                        "testDays3")));
    }

    @Test
    @SneakyThrows
    void readUncomplitedProgram_whenGetExistingActiveProgram_thenStatus200andActiveProgramReturned(){
        mockMvc.perform(get("/user/{userId}/program/active","00000000-000-0000-0000-000000000001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.days").value("testDays4"));
    }

    @Test
    @SneakyThrows
    void readUncomplitedProgram_whenGetNotExistingActiveProgram_thenStatus400andExceptionThrown(){
        mockMvc.perform(get("/user/{userId}/program/active","00000000-000-0000-0000-000000000002"))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NoEntityException))
                .andExpect(result -> assertEquals("Такой записи для Active Program в базе данных не существует",
                        result.getResolvedException().getMessage()));
    }
}