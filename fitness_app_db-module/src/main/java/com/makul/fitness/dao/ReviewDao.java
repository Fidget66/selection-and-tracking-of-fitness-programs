package com.makul.fitness.dao;

import com.makul.fitness.model.Review;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewDao extends CrudRepository <Review, Long> {
    @Query("SELECT review FROM Review review WHERE (review.authorId = ?1) and (review.fitnessProgram.id = ?2 )")
    Review findReviewByUserIdFitnessId(long userId, long fitnessProgramId);
}
