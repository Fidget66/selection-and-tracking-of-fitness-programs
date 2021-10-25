package com.makul.fitness.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.makul.fitness.model.CategoryOfFitnessProgram;
import com.makul.fitness.service.api.CategoryOfFitnessProgramService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryOfFitnessProgramControllerTestIT {
    @Autowired
    private CategoryOfFitnessProgramService categoryService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createCategory_whenAdd_thenStatus200andCategoryReturned() throws Exception {
        mockMvc.perform(post("/fitness/category")
                        .content(objectMapper.writeValueAsString(getCategory()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.shortName").value("TestCategory3"))
                .andExpect(jsonPath("$.description").value("TestDescription"));
    }

    @Test
    void showAllCategory_whenGetExistingCategoryFitnessProgramList_thenStatus200andCategoryProgramListReturned()
            throws Exception {
        mockMvc.perform(get("/fitness/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", isA(ArrayList.class)))
                .andExpect(jsonPath("$.*", hasSize(3)))
                .andExpect(jsonPath("$[*].shortName", containsInAnyOrder("TestCategory1",
                        "TestCategory2", "TestCategory3")));
    }

    private CategoryOfFitnessProgram getCategory(){
        CategoryOfFitnessProgram category = new CategoryOfFitnessProgram();
        category.setDescription("TestDescription");
        category.setShortName("TestCategory3");
        return category;
    }
}