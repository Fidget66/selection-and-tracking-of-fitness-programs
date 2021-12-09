package com.makul.fitness.dao;

import com.makul.fitness.model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UsersDao extends JpaRepository <Users, UUID> {

    @Query("SELECT user FROM Users user WHERE (user.firstName = ?1) and (user.lastName = ?2 )")
    Page <Users> findByFirstLastName(String firstName, String lastName, Pageable pageable);
}
