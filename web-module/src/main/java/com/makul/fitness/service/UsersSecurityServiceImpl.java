package com.makul.fitness.service;

import com.makul.fitness.dao.RolesDao;
import com.makul.fitness.dao.UsersSecurityDao;
import com.makul.fitness.model.Roles;
import com.makul.fitness.model.UsersSecurity;
import com.makul.fitness.service.api.UsersSecurityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional(rollbackFor = Exception.class)
    public UsersSecurity createUserSecurity(UsersSecurity usersSecurity) {
        return securityDao.save(usersSecurity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void blockUser(long userId) {
        UsersSecurity user = readByUserId(userId);
        if (!user.getRole().contains(rolesDao.findByRoleName("Admin"))){
            Roles role = rolesDao.findByRoleName("Blocked");
            user.setRole(List.of(role));
        }
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ,rollbackFor = Exception.class)
    public void unblockUser(long userId) {
        UsersSecurity user = readByUserId(userId);
        if (!user.getRole().contains(rolesDao.findByRoleName("Admin"))){
            Roles role = rolesDao.findByRoleName("Client");
            user.setRole(List.of(role));
        }
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
