package com.makul.fitness.dao;

import com.makul.fitness.model.Roles;
import org.springframework.data.repository.CrudRepository;

public interface RolesDao extends CrudRepository <Roles, Long> {
    Roles findByRoleName (String roleName);
}
