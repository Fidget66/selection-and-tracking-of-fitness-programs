package com.makul.fitness.dao;

import com.makul.fitness.model.Roles;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesDao extends CrudRepository <Roles, Long> {
    Roles findByRoleName (String roleName);
}
