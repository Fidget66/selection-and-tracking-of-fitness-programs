package com.makul.fitness.service;

import com.makul.fitness.dao.UsersDao;
import com.makul.fitness.exceptions.NoEntityException;
import com.makul.fitness.model.Users;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class UsersServiceImplTest {
    @Mock
    private UsersDao usersDao;
    @InjectMocks
    private UsersServiceImpl usersService;

    @Test
    void whenCreate_returnUser() {
        Users user = getFilledUser();
        Mockito.when(usersDao.save(user)).thenReturn(user);
        Users expected = usersService.create(user);
        Users actual = user;
        Assertions.assertEquals(expected,actual);
        Mockito.verify(usersDao, Mockito.times(1)).save(user);
    }

    @Test
    void whenRead_returnUser() {
        Users user = getFilledUser();
        UUID uuid = getUUID();
        Mockito.when(usersDao.findById(uuid)).thenReturn(Optional.ofNullable(user));
        Users expected = usersService.read(uuid);
        Users actual = user;
        Assertions.assertEquals(expected,actual);
        Mockito.verify(usersDao, Mockito.times(1)).findById(uuid);
    }

    @Test
    void whenRead_throwException() {
        UUID uuid = getUUID();
        Mockito.when(usersDao.findById(uuid)).thenReturn(Optional.empty());
        NoEntityException noEntityException = Assertions.assertThrows(NoEntityException.class,
                ()->usersService.read(uuid));
        Assertions.assertEquals(noEntityException.getMessage(),
                String.format("Такой записи для UserID=%s в базе данных не существует", uuid));
        Mockito.verify(usersDao, Mockito.times(1)).findById(uuid);
    }

    @Test
    void readUserByFirstLastName() {
        List<Users> returnList = new ArrayList<>();
        returnList.add(getFilledUser());
        returnList.add(getFilledUser());
        Pageable pageable = PageRequest.of(0,20);
        Page <Users> page = new PageImpl<>(returnList,pageable,20);
        Mockito.when(usersDao.findByFirstLastName("Test", "Test", pageable)).thenReturn(page);
        List <Users> actual = usersService.readUserByFirstLastName("Test", "Test", 0, 20)
                .getContent();
        List <Users> expected = new ArrayList<>();
        expected.add(getFilledUser());
        expected.add(getFilledUser());
        Assertions.assertEquals(expected,actual);
        Mockito.verify(usersDao,Mockito.times(1)).findByFirstLastName("Test",
                "Test", pageable);
    }

    private Users getFilledUser(){
        Users user = new Users();
        user.setFirstName("Andy");
        user.setLastName("Popov");
        user.setSex("m");
        user.setEmail("Andy@email");
        user.setWeight((short) 82);
        user.setDateOfBirth(LocalDate.of(1980,05,25));
        return user;
    }

    private UUID getUUID(){
        return UUID.randomUUID();
    }
}