package com.makul.fitness.dao;

import com.makul.fitness.model.Users;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@SqlGroup({
        @Sql("classpath:data-test.sql"),
        @Sql(scripts = "classpath:clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class UsersDaoTestIT {

    @Autowired
    private UsersDao usersDao;

    @Test
    void findByFirstLastName() {
        Pageable pageable = PageRequest.of(0,20);
        List<Users> users = usersDao.findByFirstLastName("TestName","TestSurname", pageable).getContent();
        assertNotNull(users);
        assertEquals(2, users.size());
    }
}