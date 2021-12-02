package com.makul.fitness.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.makul.fitness.dto.CategoryOfFitnessProgramDto;
import com.makul.fitness.model.CategoryOfFitnessProgram;
import com.makul.fitness.service.api.CategoryOfFitnessProgramService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Api(tags = "Controller for working with a category of fitness programs")
public class CategoryOfFitnessProgramController {

    private final CategoryOfFitnessProgramService categoryService;
    private final ObjectMapper objectMapper;

    public CategoryOfFitnessProgramController(CategoryOfFitnessProgramService categoryService, ObjectMapper objectMapper) {
        this.categoryService = categoryService;
        this.objectMapper = objectMapper;
    }

    // ToDo заиспользовать RequestMapping над классом и туда вынести перфкс ссылки /fitness/category, оставить только PostMapping
    @PostMapping("/fitness/category")
    @ApiOperation(value = "Add fitness program category")
    public CategoryOfFitnessProgramDto createCategory(@RequestBody CategoryOfFitnessProgramDto categoryDto) {
        CategoryOfFitnessProgram category = objectMapper.convertValue(categoryDto, CategoryOfFitnessProgram.class);
        return objectMapper.convertValue(categoryService.create(category), CategoryOfFitnessProgramDto.class);
    }

    @GetMapping("/fitness/categories")
    @ApiOperation(value = "Get all categories of fitness programs")
    public List<CategoryOfFitnessProgramDto> showAllCategory() {
        return categoryService.readAll().stream()
                .map(category -> objectMapper.convertValue(category, CategoryOfFitnessProgramDto.class))
                .collect(Collectors.toList());
    }
}
