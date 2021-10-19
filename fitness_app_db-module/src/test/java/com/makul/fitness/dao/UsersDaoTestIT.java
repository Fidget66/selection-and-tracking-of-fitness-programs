package com.makul.fitness.dao;

import com.makul.fitness.model.Users;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UsersDaoTestIT {

    @Autowired
    private UsersDao usersDao;

    @Test
    void findByFirstLastName() {
        List<Users> users = usersDao.findByFirstLastName("TestName","TestSurname");
        assertNotNull(users);
        assertEquals(2, users.size());
    }
}