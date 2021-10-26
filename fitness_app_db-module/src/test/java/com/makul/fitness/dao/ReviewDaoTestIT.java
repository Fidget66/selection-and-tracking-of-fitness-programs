package com.makul.fitness.dao;

import com.makul.fitness.model.Review;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@SqlGroup({
        @Sql("classpath:data-test.sql"),
        @Sql(scripts = "classpath:clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class ReviewDaoTestIT {

    @Autowired
    private ReviewDao reviewDao;

    @Test
    void findReviewByUserIdFitnessId() {
        Review review = reviewDao.findReviewByUserIdFitnessId(1,2);
        assertNotNull(review);
        assertEquals("Test review 2", review.getText());
    }
}