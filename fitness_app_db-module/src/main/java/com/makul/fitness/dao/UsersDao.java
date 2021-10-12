package com.makul.fitness.dao;

import com.makul.fitness.model.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersDao extends CrudRepository<Users, Long> {
    Users findByLogin (String login);
}
