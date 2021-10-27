package com.makul.fitness.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.makul.fitness.exceptions.IncorrectDataException;
import com.makul.fitness.exceptions.NoEntityException;
import com.makul.fitness.model.Users;
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
import static org.junit.jupiter.api.Assertions.*;
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
    void createUser_whenAdd_thenStatus200andUserReturned() throws Exception{
        mockMvc.perform(post("/user")
                        .content(objectMapper.writeValueAsString(getUser()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.firstName").value("TestName"))
                .andExpect(jsonPath("$.lastName").value("TestSurname"))
                .andExpect(jsonPath("$.email").value("test@email"))
                .andExpect(jsonPath("$.weight").value(130));
    }

    @Test
    void readUserById_whenGetExistingUser_thenStatus200andUserReturned() throws Exception {
        mockMvc.perform(get("/user/{id}", 3))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.firstName").value("Andrey"))
                .andExpect(jsonPath("$.lastName").value("Andreev"))
                .andExpect(jsonPath("$.email").value("admin@mail.ru"))
                .andExpect(jsonPath("$.weight").value(80));
    }

    @Test
    void readUserById_whenGetNotExistingUser_thenStatus400andExceptionThrown() throws Exception {
        mockMvc.perform(
                        get("/user/{id}", 90)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NoEntityException))
                .andExpect(result -> assertEquals("Такой записи для Users в базе данных не существует",
                        result.getResolvedException().getMessage()));

        mockMvc.perform(get("/user/{id}", -1))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof IncorrectDataException))
                .andExpect(result -> assertEquals("Введены некорректные данные для User id",
                        result.getResolvedException().getMessage()));
    }

    @Test
    void readUserByNameLastName_whenGetExistingUserList_thenStatus200andUserReturned() throws Exception {
        mockMvc.perform(get("/user/{firstName}/{lastName}", "Andrey", "Andreev"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", isA(ArrayList.class)))
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$[*].firstName", containsInAnyOrder("Andrey")))
                .andExpect(jsonPath("$[*].lastName", containsInAnyOrder("Andreev")))
                .andExpect(jsonPath("$[*].email", containsInAnyOrder("admin@mail.ru")))
                .andExpect(jsonPath("$[*].weight", containsInAnyOrder(80)));
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