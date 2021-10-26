package com.makul.fitness.controller;

import com.makul.fitness.exceptions.NoEntityException;
import com.makul.fitness.service.api.ActiveProgramSearcherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@SqlGroup({
        @Sql("classpath:data-test.sql"),
        @Sql(scripts = "classpath:clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class ActiveProgramSearcherControllerTestIT {

    @Autowired
    private ActiveProgramSearcherService searcherService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void readAllComplitedPrograms_whenGetExistingActiveProgramList_thenStatus200andProgramListReturned()
            throws Exception {
        mockMvc.perform(get("/user/{userId}/programs/active",1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", isA(ArrayList.class)))
                .andExpect(jsonPath("$.*", hasSize(3)))
                .andExpect(jsonPath("$[*].days", containsInAnyOrder("testDays1","testDays2",
                        "testDays3")));
    }

    @Test
    void readUncomplitedProgram_whenGetExistingActiveProgram_thenStatus200andActiveProgramReturned() throws Exception {
        mockMvc.perform(get("/user/{userId}/program/active",1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.days").value("testDays4"));
    }

    @Test
    void readUncomplitedProgram_whenGetNotExistingActiveProgram_thenStatus400andExceptionThrown() throws Exception {
        mockMvc.perform(get("/user/{userId}/program/active",2))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NoEntityException))
                .andExpect(result -> assertEquals("Такой записи для Active Program в базе данных не существует",
                        result.getResolvedException().getMessage()));
    }
}