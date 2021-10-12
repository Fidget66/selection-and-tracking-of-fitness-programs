package com.makul.fitness.service;

import com.makul.fitness.dao.RolesDao;
import com.makul.fitness.model.Roles;
import com.makul.fitness.service.api.RolesService;

public class RolesServiceImpl implements RolesService {
    private final RolesDao rolesDao;

    public RolesServiceImpl(RolesDao rolesDao) {
        this.rolesDao = rolesDao;
    }

    @Override
    public Roles readRoleByName(String roleName) {
        return rolesDao.findByRoleName(roleName);
    }
}
