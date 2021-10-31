package com.makul.fitness.service;

import com.makul.fitness.dao.ReviewDao;
import com.makul.fitness.exceptions.NoEntityException;
import com.makul.fitness.model.Review;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ReviewServiceImplTest {

    @Mock
    private ReviewDao reviewDao;
    @InjectMocks
    private ReviewServiceImpl reviewService;

    @Test
    void whenCreate_returnReview() {
        Review review = getReview();
        Mockito.when(reviewDao.save(review)).thenReturn(review);
        Review actual = reviewService.create(review);
        Review expected = review;
        Assertions.assertEquals(expected,actual);
        Mockito.verify(reviewDao, Mockito.times(1)).save(review);
    }

    @Test
    void whenRead_returnReview() {
        Review review = getReview();
        Mockito.when(reviewDao.findById(1L)).thenReturn(Optional.of(review));
        Review actual = reviewService.read(1L);
        Review expected = review;
        Assertions.assertEquals(expected,actual);
        Mockito.verify(reviewDao, Mockito.times(1)).findById(1L);
    }

    @Test
    void whenRead_throwException() {
        Review review = getReview();
        Mockito.when(reviewDao.findById(4L)).thenReturn(Optional.empty());
        NoEntityException noEntityException = Assertions.assertThrows(NoEntityException.class,
                ()->reviewService.read(4L));
        Assertions.assertEquals(noEntityException.getMessage(),
                "Такой записи для Review в базе данных не существует");
        Mockito.verify(reviewDao, Mockito.times(1)).findById(4L);
    }

    @Test
    void whenUpdate_returnReview() {
        Review review = getReview();
        Mockito.when(reviewDao.findById(1L)).thenReturn(Optional.ofNullable(review));
        Mockito.when(reviewDao.save(review)).thenReturn(review);
        Review actual = reviewService.update(review);
        Review expected = review;
        Assertions.assertEquals(expected,actual);
        Mockito.verify(reviewDao, Mockito.times(1)).findById(1L);
        Mockito.verify(reviewDao,Mockito.times(1)).save(review);
    }

    private Review getReview(){
        Review review = new Review();
        review.setId(1);
        review.setText("Test review");
        review.setAuthorId(1);
        return review;
    }
}