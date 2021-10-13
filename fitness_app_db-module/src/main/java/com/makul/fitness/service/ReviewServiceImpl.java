package com.makul.fitness.service;

import com.makul.fitness.dao.ReviewDao;
import com.makul.fitness.exceptions.IncorrectDataException;
import com.makul.fitness.exceptions.NoEntityException;
import com.makul.fitness.model.Review;
import com.makul.fitness.service.api.ReviewService;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewDao reviewDao;
    public ReviewServiceImpl(ReviewDao reviewDao) {
        this.reviewDao = reviewDao;
    }

    @Override
    public Review create(Review review) {
        return reviewDao.save(review);
    }

    @Override
    public Review read(long id) {
        if (id<1) throw new IncorrectDataException("Review id");
        return reviewDao.findById(id).orElseThrow(()->new NoEntityException("Review"));
    }

    @Override
    public Review update(Review review) {
        return reviewDao.save(review);
    }
}
