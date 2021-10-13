package com.makul.fitness.service.api;

import com.makul.fitness.model.Review;

public interface ReviewService {
    Review create(Review review);
    Review read(long id);
    Review update(Review review);
}
