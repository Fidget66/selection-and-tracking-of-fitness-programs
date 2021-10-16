package com.makul.fitness.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.makul.fitness.dto.ReviewDto;
import com.makul.fitness.model.Review;
import com.makul.fitness.service.api.ReviewService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReviewController {

    private final ObjectMapper objectMapper;
    private final ReviewService reviewService;

    public ReviewController(ObjectMapper objectMapper, ReviewService reviewService) {
        this.objectMapper = objectMapper;
        this.reviewService = reviewService;
    }

    @PutMapping("/review")
    public ReviewDto updateReview(@RequestBody ReviewDto reviewDto){
        Review review = objectMapper.convertValue(reviewDto, Review.class);
        return objectMapper.convertValue(reviewService.update(review), ReviewDto.class);
    }
}
