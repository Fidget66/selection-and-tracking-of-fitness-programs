package com.makul.fitness.dao;

import com.makul.fitness.model.ExerciseSchedule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@SqlGroup({
        @Sql("classpath:data-test.sql"),
        @Sql(scripts = "classpath:clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class ExerciseScheduleSearcherDaoTestIT {

    @Autowired
    private ExerciseScheduleSearcherDao scheduleDao;

    @Test
    void findExerciseByDate() {
        List<ExerciseSchedule> scheduleList = scheduleDao.findByExerciseDate(LocalDate.of(2021,01,01));
        Assertions.assertNotNull(scheduleList);
        Assertions.assertEquals(2,scheduleList.size());
    }

    @Test
    void findExerciseByProgramId() {
        Pageable pageable = PageRequest.of(0,30);
        List<ExerciseSchedule> scheduleList = scheduleDao.findByActiveProgram_IdOrderByExerciseDateAsc
                (UUID.fromString("00000000-000-0000-0000-000000000013"),pageable).getContent();
        Assertions.assertNotNull(scheduleList);
        Assertions.assertEquals(4,scheduleList.size());
    }
}