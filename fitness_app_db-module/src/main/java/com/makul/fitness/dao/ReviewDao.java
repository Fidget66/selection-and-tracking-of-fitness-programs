package com.makul.fitness.dao;

import com.makul.fitness.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReviewDao extends JpaRepository <Review, UUID> {

    @Query("SELECT review FROM Review review WHERE (review.authorId = ?1) and (review.fitnessProgram.id = ?2 )")
    Review findReviewByUserIdFitnessId(UUID userId, UUID fitnessProgramId);
}
