package com.makul.fitness.dao;

import com.makul.fitness.model.ActiveProgram;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
class ActiveProgramSearcherDaoTestIT {

    @Autowired
    private ActiveProgramSearcherDao searcherDao;

    @Test
    void findComplitedActiveProgram() {
        Page<ActiveProgram> page = searcherDao.findActiveProgramsByUserIdAndIsComplitedTrue(getUUID(), PageRequest.of(0,20));
        List<ActiveProgram> programList=page.getContent();
        Assertions.assertNotNull(programList);
        Assertions.assertEquals(3,programList.size());
    }

    @Test
    void findUncomplitedActiveProgram() {
        ActiveProgram activeProgram = searcherDao.findActiveProgramsByUserIdAndIsComplitedFalse(getUUID()).orElseThrow();
        Assertions.assertNotNull(activeProgram);
        Assertions.assertEquals("testDays4",activeProgram.getDays());
    }

    private UUID getUUID(){
        return UUID.fromString("00000000-000-0000-0000-000000000001");
    }
}