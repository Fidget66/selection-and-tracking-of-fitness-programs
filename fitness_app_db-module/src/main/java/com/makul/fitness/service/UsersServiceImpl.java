package com.makul.fitness.service;

import com.makul.fitness.dao.UsersDao;
import com.makul.fitness.exceptions.NoEntityException;
import com.makul.fitness.model.Users;
import com.makul.fitness.service.api.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

    private final UsersDao usersDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Users create(Users user) {
        return usersDao.save(user);
    }

    @Override
    public Users read(UUID id) {
        return usersDao.findById(id).orElseThrow(()->new NoEntityException("UserID=" + id));
    }

    @Override
    public Page <Users> readUserByFirstLastName(String firstName, String lastName, int pageNumber, int size) {
        Pageable pageable = PageRequest.of(pageNumber, size);
        return usersDao.findByFirstLastName(firstName,lastName, pageable);
    }
}
