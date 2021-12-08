package com.makul.fitness.controller;

import com.makul.fitness.dto.CategoryOfFitnessProgramDto;
import com.makul.fitness.model.CategoryOfFitnessProgram;
import com.makul.fitness.service.api.CategoryOfFitnessProgramService;
import com.makul.fitness.util.CustomMapperUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fitness/category")
@RequiredArgsConstructor
@Api(tags = "Controller for working with a category of fitness programs")
public class CategoryOfFitnessProgramController {

    private final CategoryOfFitnessProgramService categoryService;

    @PostMapping
    @ApiOperation(value = "Add fitness program category")
    public CategoryOfFitnessProgramDto createCategory(@RequestBody CategoryOfFitnessProgramDto categoryDto) {
        CategoryOfFitnessProgram category = CustomMapperUtil.convertToDto(categoryDto, CategoryOfFitnessProgram.class);
        return CustomMapperUtil.convertToDto(categoryService.create(category), CategoryOfFitnessProgramDto.class);
    }

    @GetMapping
    @ApiOperation(value = "Get all categories of fitness programs")
    public Page<CategoryOfFitnessProgramDto> showAllCategory (@ApiParam(defaultValue = "0") @RequestParam int page,
                                                              @ApiParam(defaultValue = "10") @RequestParam int size) {
        return CustomMapperUtil.convertToDto(categoryService.readAll(page, size), CategoryOfFitnessProgramDto.class);
    }
}
