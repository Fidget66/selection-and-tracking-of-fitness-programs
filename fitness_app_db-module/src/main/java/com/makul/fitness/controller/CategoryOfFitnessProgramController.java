package com.makul.fitness.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.makul.fitness.dto.CategoryOfFitnessProgramDto;
import com.makul.fitness.model.CategoryOfFitnessProgram;
import com.makul.fitness.service.api.CategoryOfFitnessProgramService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CategoryOfFitnessProgramController {

    private final CategoryOfFitnessProgramService categoryService;
    private final ObjectMapper objectMapper;

    public CategoryOfFitnessProgramController(CategoryOfFitnessProgramService categoryService, ObjectMapper objectMapper) {
        this.categoryService = categoryService;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/fitness/category")
    public CategoryOfFitnessProgramDto createCategory(@RequestBody CategoryOfFitnessProgramDto categoryDto){
        CategoryOfFitnessProgram category = objectMapper.convertValue(categoryDto,CategoryOfFitnessProgram.class);
        return objectMapper.convertValue(categoryService.create(category),CategoryOfFitnessProgramDto.class);
    }

    @GetMapping("/fitness/categories")
    public List<CategoryOfFitnessProgramDto> showAllCategory(){
        return categoryService.readAll().stream()
                .map(category -> objectMapper.convertValue(category,CategoryOfFitnessProgramDto.class))
                .collect(Collectors.toList());
    }
}
