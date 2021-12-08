package com.makul.fitness.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.makul.fitness.exceptions.NoEntityException;
import com.makul.fitness.model.Users;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.hamcrest.Matchers.*;
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
class UsersControllerTestIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @SneakyThrows
    void createUser_whenAdd_thenStatus200andUserReturned(){
        mockMvc.perform(post("/user")
                        .content(objectMapper.writeValueAsString(getUser()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.firstName").value("TestName"))
                .andExpect(jsonPath("$.lastName").value("TestSurname"))
                .andExpect(jsonPath("$.email").value("test@email"))
                .andExpect(jsonPath("$.weight").value(130));
    }

    @Test
    @SneakyThrows
    void readUserById_whenGetExistingUser_thenStatus200andUserReturned(){
        mockMvc.perform(get("/user/{id}", "00000000-0000-0000-0000-000000000003"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.firstName").value("Andrey"))
                .andExpect(jsonPath("$.lastName").value("Andreev"))
                .andExpect(jsonPath("$.email").value("admin@mail.ru"))
                .andExpect(jsonPath("$.weight").value(80));
    }

    @Test
    @SneakyThrows
    void readUserById_whenGetNotExistingUser_thenStatus400andExceptionThrown(){
        mockMvc.perform(
                        get("/user/{id}", "00000000-000-0000-0000-000000000068")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NoEntityException))
                .andExpect(result -> assertEquals(String.format("Такой записи для UserID=" +
                                "00000000-0000-0000-0000-000000000068 в базе данных не существует"),
                        result.getResolvedException().getMessage()));
    }

    @Test
    @SneakyThrows
    void readUserByNameLastName_whenGetExistingUserList_thenStatus200andUserReturned(){
        mockMvc.perform(get("/user/{firstName}/{lastName}", "Andrey", "Andreev")
                        .param("page","0")
                        .param("size","20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", isA(ArrayList.class)))
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[*].firstName", containsInAnyOrder("Andrey")))
                .andExpect(jsonPath("$.content[*].lastName", containsInAnyOrder("Andreev")))
                .andExpect(jsonPath("$.content[*].email", containsInAnyOrder("admin@mail.ru")))
                .andExpect(jsonPath("$.content[*].weight", containsInAnyOrder(80)));
    }

    private Users getUser(){
        Users user = new Users();
        user.setFirstName("TestName");
        user.setLastName("TestSurname");
        user.setEmail("test@email");
        user.setDateOfBirth(LocalDate.of(2021, 01, 01));
        user.setSex("m");
        user.setWeight(130);
        return user;
    }
}