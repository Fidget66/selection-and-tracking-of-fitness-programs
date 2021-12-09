package com.makul.fitness.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.makul.fitness.model.CategoryOfFitnessProgram;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

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
class CategoryOfFitnessProgramControllerTestIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @SneakyThrows
    void createCategory_whenAdd_thenStatus200andCategoryReturned(){
        mockMvc.perform(post("/fitness/category")
                        .content(objectMapper.writeValueAsString(getCategory()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.shortName").value("TestCategory3"))
                .andExpect(jsonPath("$.description").value("TestDescription"));
    }

    @Test
    @SneakyThrows
    void showAllCategory_whenGetExistingCategoryFitnessProgramList_thenStatus200andCategoryProgramListReturned(){
        mockMvc.perform(get("/fitness/category")
                        .param("page","0")
                        .param("size","20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*").isNotEmpty())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[*].id", notNullValue()))
                .andExpect(jsonPath("$.content[*].shortName", containsInAnyOrder("TestCategory1",
                        "TestCategory2")));
    }

    private CategoryOfFitnessProgram getCategory(){
        CategoryOfFitnessProgram category = new CategoryOfFitnessProgram();
        category.setDescription("TestDescription");
        category.setShortName("TestCategory3");
        return category;
    }
}