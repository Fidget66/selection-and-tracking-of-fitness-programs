package com.makul.fitness.service;

import com.makul.fitness.dao.UsersDao;
import com.makul.fitness.exceptions.IncorrectDataException;
import com.makul.fitness.exceptions.NoEntityException;
import com.makul.fitness.model.Users;
import com.makul.fitness.service.api.UsersService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

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
        Mockito.when(usersDao.findById(1L)).thenReturn(Optional.ofNullable(user));
        Users expected = usersService.read(1L);
        Users actual = user;
        Assertions.assertEquals(expected,actual);
        Mockito.verify(usersDao, Mockito.times(1)).findById(1L);
    }

    @Test
    void whenRead_throwException() {
        Mockito.when(usersDao.findById(4L)).thenReturn(Optional.empty());
        NoEntityException noEntityException = Assertions.assertThrows(NoEntityException.class,
                ()->usersService.read(4L));
        Assertions.assertEquals(noEntityException.getMessage(),
                "Такой записи для Users в базе данных не существует");
        Mockito.verify(usersDao, Mockito.times(1)).findById(4L);

        IncorrectDataException incorrectDataException = Assertions.assertThrows(IncorrectDataException.class,
                ()->usersService.read(-1L));
        Assertions.assertEquals(incorrectDataException.getMessage(),
                "Введены некорректные данные для User id");
        Mockito.verify(usersDao, Mockito.times(0)).findById(-1L);
    }

    @Test
    void readUserByFirstLastName() {
        List<Users> returnList = new ArrayList<>();
        returnList.add(getFilledUser());
        returnList.add(getFilledUser());
        Mockito.when(usersDao.findByFirstLastName("Test", "Test")).thenReturn(returnList);
        List <Users> actual = usersService.readUserByFirstLastName("Test", "Test");
        List <Users> expected = new ArrayList<>();
        expected.add(getFilledUser());
        expected.add(getFilledUser());
        Assertions.assertEquals(expected,actual);
        Mockito.verify(usersDao,Mockito.times(1)).findByFirstLastName("Test", "Test");
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
}