package com.makul.fitness.service;

import com.makul.fitness.dao.UsersDao;
import com.makul.fitness.exceptions.IncorrectDataException;
import com.makul.fitness.exceptions.NoEntityException;
import com.makul.fitness.model.Users;
import com.makul.fitness.service.api.UsersService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsersServiceImpl implements UsersService {

    private final UsersDao usersDao;
    public UsersServiceImpl(UsersDao usersDao) {
        this.usersDao = usersDao;
    }

    @Override
    @Transactional
    public Users create(Users user) {
        return usersDao.save(user);
    }

    @Override
    public Users read(long id) {
        if (id<1) throw new IncorrectDataException("User id");
        return usersDao.findById(id).orElseThrow(()->new NoEntityException("Users"));
    }

    @Override
    public List<Users> readUserByFirstLastName(String firstName, String lastName) {
        return usersDao.findByFirstLastName(firstName,lastName);
    }
}
