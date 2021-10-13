package com.makul.fitness.service.api;

import com.makul.fitness.model.Users;

import java.util.List;

public interface UsersService {
    Users create(Users user);
    Users read(long id);
    Users update(Users user);
}
