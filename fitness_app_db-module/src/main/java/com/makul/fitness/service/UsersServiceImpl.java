package com.makul.fitness.service;

import com.makul.fitness.dao.UsersDao;
import com.makul.fitness.model.Users;
import com.makul.fitness.service.api.RolesService;
import com.makul.fitness.service.api.UsersService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

public class UsersServiceImpl implements UsersService {

    private final RolesService rolesService;
    private final UsersDao usersDao;

    public UsersServiceImpl(RolesService rolesService, UsersDao usersDao) {
        this.rolesService = rolesService;
        this.usersDao = usersDao;
    }

    @Override
    @Transactional
    public Users create(Users user) {
        user.setRole(Set.of(rolesService.readRoleByName("CLIENT")));
        user.setAccountNonLocked(true);
        return usersDao.save(user);
    }

    @Override
    public Users readByLogin(String login) {
        return usersDao.findByLogin(login);
    }
}
