package com.makul.fitness.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.makul.fitness.dto.ReviewDto;
import com.makul.fitness.model.Review;
import com.makul.fitness.service.api.ReviewService;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/review/{id}")
    public ReviewDto readReview(@PathVariable("id") long id){
        return objectMapper.convertValue(reviewService.read(id), ReviewDto.class);
    }
}
