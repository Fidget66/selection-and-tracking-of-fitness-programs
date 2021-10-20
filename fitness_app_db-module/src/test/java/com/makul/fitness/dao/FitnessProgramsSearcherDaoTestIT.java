package com.makul.fitness.dao;

import com.makul.fitness.model.FitnessProgram;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class FitnessProgramsSearcherDaoTestIT {

    @Autowired
    private FitnessProgramsSearcherDao searcherDao;

    @Test
    void findFitnessProgramWithRestrictions() {
        List <FitnessProgram> programList = searcherDao.findFitnessProgramWithRestrictions(1,10);
        Assertions.assertNotNull(programList);
        Assertions.assertEquals(1, programList.size());
    }

    @Test
    void findFitnessProgram() {
        List <FitnessProgram> programList = searcherDao.findFitnessProgram(1);
        Assertions.assertNotNull(programList);
        Assertions.assertEquals(3, programList.size());
    }
}