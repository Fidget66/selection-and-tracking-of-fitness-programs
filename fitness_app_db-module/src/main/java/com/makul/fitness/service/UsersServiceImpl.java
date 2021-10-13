package com.makul.fitness.service;

import com.makul.fitness.dao.UsersDao;
import com.makul.fitness.exceptions.IncorrectDataException;
import com.makul.fitness.exceptions.NoEntityException;
import com.makul.fitness.model.Bookmark;
import com.makul.fitness.model.Users;
import com.makul.fitness.service.api.UsersService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
        if (id<1) throw new IncorrectDataException("user id");
        return usersDao.findById(id).orElseThrow(()->new NoEntityException("Users"));
    }

    @Override
    public List<Users> readAll() {
        return StreamSupport.stream(usersDao.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Users update(Users user) {
        Users outputUser = read(user.getId());
        if (Objects.nonNull(user.getBookmarks()) && !user.getBookmarks().isEmpty())
            for (Bookmark bookmark: user.getBookmarks()){
                outputUser.getBookmarks().add(bookmark);
            }
        return usersDao.save(outputUser);
    }

    @Override
    @Transactional
    public void delete(long id) {
        if (id<1) throw new IncorrectDataException("user id");
        usersDao.deleteById(id);
    }
}
