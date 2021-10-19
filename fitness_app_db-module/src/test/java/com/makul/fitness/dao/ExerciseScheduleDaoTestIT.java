package com.makul.fitness.dao;

import com.makul.fitness.model.ExerciseSchedule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.time.LocalDate;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ExerciseScheduleDaoTestIT {

    @Autowired
    private ExerciseScheduleDao scheduleDao;

    @Test
    void findExerciseByDate() {
        List<ExerciseSchedule> scheduleList = scheduleDao.findExerciseByDate(LocalDate.of(2021,01,01));
        Assertions.assertNotNull(scheduleList);
        Assertions.assertEquals(2,scheduleList.size());
    }
}