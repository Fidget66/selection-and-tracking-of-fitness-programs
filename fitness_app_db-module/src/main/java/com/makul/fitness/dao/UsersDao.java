package com.makul.fitness.dao;

import com.makul.fitness.model.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersDao extends CrudRepository<Users, Long> {
    @Query("SELECT user FROM Users user WHERE (user.firstName = ?1) and (user.lastName = ?2 )")
    List<Users> findByFirstLastName(String firstName, String lastName);
}
