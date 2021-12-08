package com.makul.fitness.controller;

import com.makul.fitness.dto.FiltredDto;
import com.makul.fitness.dto.FitnessProgramDto;
import com.makul.fitness.service.api.FitnessProgramsSearcherService;
import com.makul.fitness.util.CustomMapperUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Api(tags = "Controller for search fitness programs")
public class FitnessProgramSearcherController {

    private final FitnessProgramsSearcherService fitnessSearcherService;

    @GetMapping("/category/{id}/program/fitness")
    @ApiOperation(value = "Get fitness programs of current category ")
    public Page <FitnessProgramDto> readFitnessProgramList(@ApiParam(defaultValue = "2") @PathVariable("id") UUID categoryId,
                                                          @ApiParam(defaultValue = "0") @RequestParam int page,
                                                          @ApiParam(defaultValue = "10") @RequestParam int size) {
        return CustomMapperUtil.convertToDto(fitnessSearcherService.readFitnessProgram(categoryId, page, size),
                FitnessProgramDto.class);
    }

    @PostMapping("/user/program/fitness")
    @ApiOperation(value = "Get restricted fitness programs")
    public Page <FitnessProgramDto> readFitnessProgramListWithRestrictions(@RequestBody FiltredDto filtredDto,
                                                                          @ApiParam(defaultValue = "0") @RequestParam int page,
                                                                          @ApiParam(defaultValue = "10") @RequestParam int size) {
        return CustomMapperUtil.convertToDto(fitnessSearcherService.readFitnessProgramWithRestrictions(filtredDto.getUserId(),
                        filtredDto.getDuration(), filtredDto.getCategoryId(), page, size), FitnessProgramDto.class);
    }
}
