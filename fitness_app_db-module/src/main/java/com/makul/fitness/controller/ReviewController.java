package com.makul.fitness.controller;

import com.makul.fitness.dto.ReviewDto;
import com.makul.fitness.model.Review;
import com.makul.fitness.service.api.ClientBusinessService;
import com.makul.fitness.service.api.ReviewService;
import com.makul.fitness.util.CustomMapperUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Api(tags = "Controller for work with reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final ClientBusinessService clientBusinessService;

    @PostMapping("/user/program/fitness/{fitnessProgramId}/review")
    @ApiOperation(value = "Add a review about fitness program")
    public ReviewDto addReview(@ApiParam(defaultValue = "6") @PathVariable UUID fitnessProgramId,
                               @RequestBody ReviewDto reviewDto) {
        Review review = CustomMapperUtil.convertToDto(reviewDto, Review.class);
        return CustomMapperUtil.convertToDto(clientBusinessService.addReview(fitnessProgramId, review), ReviewDto.class);
    }

    @PutMapping("/review")
    @ApiOperation(value = "Update review")
    public ReviewDto updateReview(@RequestBody ReviewDto reviewDto) {
        Review review = CustomMapperUtil.convertToDto(reviewDto, Review.class);
        return CustomMapperUtil.convertToDto(reviewService.update(review), ReviewDto.class);
    }

    @GetMapping("/review/{id}")
    @ApiOperation(value = "Find review by id")
    public ReviewDto readReview(@ApiParam(defaultValue = "1") @PathVariable("id") UUID id) {
        return CustomMapperUtil.convertToDto(reviewService.read(id), ReviewDto.class);
    }
}
