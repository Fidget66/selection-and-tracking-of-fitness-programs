package com.makul.fitness.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.makul.fitness.exceptions.BusinessException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@SqlGroup({
        @Sql("classpath:data-test.sql"),
        @Sql(scripts = "classpath:clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class BookmarkControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @SneakyThrows
    void deleteBookmark_whenDeleteExistingActiveProgram_thenStatus200(){
        mockMvc.perform(delete("/bookmark/{id}","00000000-000-0000-0000-000000000027"))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void addBookmark_whenAdd_thenStatus200andBookmarkReturned(){
        mockMvc.perform(get("/user/{userId}/bookmark/{fitnessId}","00000000-0000-0000-0000-000000000002",
                        "00000000-0000-0000-0000-000000000007"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.fitnessProgram").isNotEmpty());
    }

    @Test
    @SneakyThrows
    void addBookmark_whenAddExistingBookmark_thenStatus400andExceptionThrown(){
        mockMvc.perform(
                        get("/user/{userId}/bookmark/{fitnessId}", "00000000-0000-0000-0000-000000000001",
                                "00000000-0000-0000-0000-000000000007"))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BusinessException))
                .andExpect(result -> assertEquals("Такая закладка уже есть у Вас.",
                        result.getResolvedException().getMessage()));
    }

    @Test
    @SneakyThrows
    void showBookmarks_whenGetExistingBookmarkList_thenStatus200andBookmarkListReturned(){
        mockMvc.perform(get("/user/{userId}/bookmarks", "00000000-0000-0000-0000-000000000001")
                        .param("page","0")
                        .param("size","20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[*].id", notNullValue()))
                .andExpect(jsonPath("$.*").isNotEmpty());
    }

}