package com.makul.fitness.service;

import com.makul.fitness.dao.RolesDao;
import com.makul.fitness.dao.UsersSecurityDao;
import com.makul.fitness.model.Roles;
import com.makul.fitness.model.UsersSecurity;
import com.makul.fitness.service.api.UsersSecurityService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UsersSecurityServiceImpl implements UsersSecurityService {

    private final UsersSecurityDao securityDao;
    private final RolesDao rolesDao;

    public UsersSecurityServiceImpl(UsersSecurityDao securityDao, RolesDao rolesDao) {
        this.securityDao = securityDao;
        this.rolesDao = rolesDao;
    }

    @Override
    public UsersSecurity createUserSecurity(UsersSecurity usersSecurity) {
        return securityDao.save(usersSecurity);
    }

    @Override
    public void blockUser(long userId) {
        UsersSecurity user = readByUserId(userId);
        Roles role = rolesDao.findByRoleName("Blocked");
        user.setRole(List.of(role));
    }

    @Override
    public UsersSecurity readByLogin(String login) {
        return securityDao.findByLogin(login);
    }

    @Override
    public UsersSecurity readByUserId(long userId) {
        return securityDao.findByUserId(userId);
    }
}