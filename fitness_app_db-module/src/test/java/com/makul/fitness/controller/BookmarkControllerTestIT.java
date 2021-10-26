package com.makul.fitness.controller;

import com.makul.fitness.exceptions.IncorrectDataException;
import com.makul.fitness.service.api.BookmarkService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@SqlGroup({
        @Sql("classpath:data-test.sql"),
        @Sql(scripts = "classpath:clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class BookmarkControllerTestIT {
    @Autowired
    private BookmarkService bookmarkService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void deleteBookmark_whenDeleteExistingActiveProgram_thenStatus200() throws Exception {
        mockMvc.perform(delete("/bookmark/{id}",1))
                .andExpect(status().isOk());
    }

    @Test
    void deleteBookmark_whenDeleteExistingActiveProgram_thenStatus400() throws Exception {
        mockMvc.perform(delete("/bookmark/{id}",-1))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof IncorrectDataException))
                .andExpect(result -> assertEquals("Введены некорректные данные для Bookmark id",
                        result.getResolvedException().getMessage()));
    }
}