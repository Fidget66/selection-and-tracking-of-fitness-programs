package com.makul.fitness.service;

import com.makul.fitness.config.UserDetail;
import com.makul.fitness.dao.UsersSecurityDao;
import com.makul.fitness.model.UsersSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsersSecurityDao usersDao;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UsersSecurity user = usersDao.findByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }
        return new UserDetail(user);
    }
}
