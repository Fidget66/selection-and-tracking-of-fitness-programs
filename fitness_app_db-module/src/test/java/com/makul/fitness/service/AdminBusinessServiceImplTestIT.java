package com.makul.fitness.service;

import com.makul.fitness.model.FitnessProgram;
import com.makul.fitness.service.api.AdminBusinessService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@SqlGroup({
        @Sql("classpath:data-test.sql"),
        @Sql(scripts = "classpath:clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class AdminBusinessServiceImplTestIT {

    @Autowired
    private AdminBusinessService adminBusinessService;

    @Test
    void addFitnessProgram_thenReturnFitnessProgram(){
        FitnessProgram fitnessProgram = adminBusinessService.addFitnessProgram(UUID.fromString("00000000-000-0000-0000-000000000005"),
                getFitnessProgram());
        assertNotNull(fitnessProgram);
        assertTrue(fitnessProgram.getId().toString()!="");
        assertEquals("Test", fitnessProgram.getShortName());
        assertEquals("Test description",fitnessProgram.getDescription());
        assertEquals(66, fitnessProgram.getWeightRestriction());
    }

    private FitnessProgram getFitnessProgram(){
        FitnessProgram fitnessProgram = new FitnessProgram();
        fitnessProgram.setShortName("Test");
        fitnessProgram.setDescription("Test description");
        fitnessProgram.setAgeRestriction(30);
        fitnessProgram.setExercisePerWeek(3);
        fitnessProgram.setWeightRestriction(66);
        fitnessProgram.setDuration(3);
        return fitnessProgram;
    }
}
