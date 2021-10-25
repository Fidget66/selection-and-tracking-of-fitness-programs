package com.makul.fitness.controller;

import com.makul.fitness.service.api.FitnessProgramsSearcherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FitnessProgramSearcherControllerTestIT {

    @Autowired
    private FitnessProgramsSearcherService searcherService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void readFitnessProgramList_whenGetExistingFitnessProgramList_thenStatus200andProgramListReturned() throws Exception {
        mockMvc.perform(get("/category/{id}/program/fitness",1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", isA(ArrayList.class)))
                .andExpect(jsonPath("$.*", hasSize(3)))
                .andExpect(jsonPath("$[*].shortName", containsInAnyOrder("TestProgram1","TestProgram2",
                        "TestProgram3")));
    }

    @Test
    void readFitnessProgramListWithRestrictions_whenGetExistingFitnessProgramList_thenStatus200andProgramListReturned()
            throws Exception {
        mockMvc.perform(get("/user/{userId}/program/fitness/{duration}",1,50))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", isA(ArrayList.class)))
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$[*].shortName", containsInAnyOrder("TestProgram1")));
    }
}