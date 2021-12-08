package com.makul.fitness.service.api;

import com.makul.fitness.model.Review;

import java.util.UUID;

public interface ReviewService {
    Review create(Review review);
    Review read(UUID id);
    Review readReviewByUserIdFitnessId(UUID userId, UUID fitnessProgramId);
    Review update(Review review);
}
