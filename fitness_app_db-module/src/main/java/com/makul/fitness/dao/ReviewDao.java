package com.makul.fitness.dao;

import com.makul.fitness.model.Review;
import org.springframework.data.repository.CrudRepository;

public interface ReviewDao extends CrudRepository <Review, Long> {
}
