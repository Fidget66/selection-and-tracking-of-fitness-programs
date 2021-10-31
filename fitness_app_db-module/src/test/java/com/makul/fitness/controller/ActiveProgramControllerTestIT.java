package com.makul.fitness.controller;

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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@SqlGroup({
        @Sql("classpath:data-test.sql"),
        @Sql(scripts = "classpath:clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class ActiveProgramControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    void readProgram_whenGetExistingActiveProgram_thenStatus200andProgramReturned(){
        mockMvc.perform(get("/program/active/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.days").value("testDays1"));
    }

    @Test
    @SneakyThrows
    void readProgram_whenGetNotExistingActiveProgram_thenStatus400andExceptionThrown(){
            mockMvc.perform(
                            get("/program/active/{id}", 90)
                                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof NoEntityException))
                    .andExpect(result -> assertEquals("Такой записи для Active Program в базе данных не существует",
                            result.getResolvedException().getMessage()));
    }
}