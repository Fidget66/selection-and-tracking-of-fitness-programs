package com.makul.fitness.service.api;

import com.makul.fitness.model.Roles;

public interface RolesService {
    Roles readRoleByName(String roleName);
}
