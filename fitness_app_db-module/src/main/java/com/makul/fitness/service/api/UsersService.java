package com.makul.fitness.service.api;

import com.makul.fitness.model.Users;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface UsersService {
    Users create(Users user);
    Users read(UUID id);
    Page <Users> readUserByFirstLastName(String firstName, String lastName,  int pageNumber, int size);
}
