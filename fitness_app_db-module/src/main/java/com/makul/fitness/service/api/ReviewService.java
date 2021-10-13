package com.makul.fitness.service.api;

import com.makul.fitness.model.Review;
import java.util.List;

public interface ReviewService {
    Review create(Review review);
    Review read(long id);
    List<Review> readAll();
    void deleteById(long id);
}
