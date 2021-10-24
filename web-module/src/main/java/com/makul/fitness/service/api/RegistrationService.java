package com.makul.fitness.service.api;

import com.makul.fitness.dto.UsersDto;
import com.makul.fitness.model.UsersSecurity;

public interface RegistrationService {
    UsersSecurity registerUser(UsersDto user);
}
