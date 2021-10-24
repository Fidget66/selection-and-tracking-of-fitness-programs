package com.makul.fitness.service.api;

import com.makul.fitness.model.UsersSecurity;

public interface UsersSecurityService {
    UsersSecurity createUserSecurity(UsersSecurity usersSecurity);
    void blockUser(long userId);
    UsersSecurity readByLogin(String login);
    UsersSecurity readByUserId(long userId);
}
