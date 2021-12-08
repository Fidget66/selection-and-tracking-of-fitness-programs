package com.makul.fitness.dao;

import com.makul.fitness.model.FitnessProgram;
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

import java.util.List;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@SqlGroup({
        @Sql("classpath:data-test.sql"),
        @Sql(scripts = "classpath:clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class FitnessProgramsSearcherDaoTestIT {

    @Autowired
    private FitnessProgramsSearcherDao searcherDao;

    @Test
    void findFitnessProgramWithRestrictions() {
        Pageable pageable = PageRequest.of(0,30);
        List <FitnessProgram> programList = searcherDao.findFitnessProgramWithRestrictions(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                30,UUID.fromString("00000000-000-0000-0000-000000000005"),35,pageable).getContent();
        Assertions.assertNotNull(programList);
        Assertions.assertEquals(1, programList.size());
    }

    @Test
    void findFitnessProgram() {
        Pageable pageable = PageRequest.of(0,30);
        List <FitnessProgram> programList = searcherDao.findFitnessProgram(UUID.fromString("00000000-0000-0000-0000-000000000005"),
                pageable).getContent();
        Assertions.assertNotNull(programList);
        Assertions.assertEquals(3, programList.size());
    }
}