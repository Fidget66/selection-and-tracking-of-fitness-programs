package com.makul.fitness.dao;

import com.makul.fitness.model.UsersSecurity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersSecurityDao extends CrudRepository <UsersSecurity,Long> {
}
