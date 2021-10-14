package com.makul.fitness.service.api;

import com.makul.fitness.model.Users;

public interface UsersService {
    Users create(Users user);
    Users read(long id);
}
