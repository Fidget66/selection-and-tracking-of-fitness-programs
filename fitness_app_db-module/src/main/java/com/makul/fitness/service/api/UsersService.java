package com.makul.fitness.service.api;

import com.makul.fitness.model.Users;

import java.util.List;

public interface UsersService {
    Users create(Users user);
    Users read(long id);
    List<Users> readAll();
    Users update(Users user);
    void delete(long id);
}
