package com.makul.fitness.service;

import com.makul.fitness.dao.ReviewDao;
import com.makul.fitness.exceptions.NoEntityException;
import com.makul.fitness.model.Review;
import com.makul.fitness.service.api.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewDao reviewDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Review create(Review review) {
        return reviewDao.save(review);
    }

    @Override
    public Review read(UUID id) {
        return reviewDao.findById(id).orElseThrow(()->new NoEntityException("Review Id=" + id));
    }

    @Override
    public Review readReviewByUserIdFitnessId(UUID userId, UUID fitnessProgramId) {
        return reviewDao.findReviewByUserIdFitnessId(userId,fitnessProgramId);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
    public Review update(Review inputReview) {
        Review review = read(inputReview.getId());
        review.setText(inputReview.getText());
        return reviewDao.save(review);
    }
}
