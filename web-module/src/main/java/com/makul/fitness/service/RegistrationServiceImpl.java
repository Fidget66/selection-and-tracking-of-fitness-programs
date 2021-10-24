package com.makul.fitness.service;

import com.makul.fitness.dao.RolesDao;
import com.makul.fitness.dto.UsersDto;
import com.makul.fitness.model.Roles;
import com.makul.fitness.model.UsersSecurity;
import com.makul.fitness.service.api.RegistrationService;
import com.makul.fitness.service.api.UsersSecurityService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final RestTemplate restTemplate;
    private final UsersSecurityService securityService;
    private final RolesDao rolesDao;
    private final BCryptPasswordEncoder passwordEncoder;
    private final String baseURL = "http://localhost:8124/fitnessDB-app/";

    public RegistrationServiceImpl(RestTemplate restTemplate, UsersSecurityService securityService,
                                   RolesDao rolesDao, BCryptPasswordEncoder passwordEncoder) {
        this.restTemplate = restTemplate;
        this.securityService = securityService;
        this.rolesDao = rolesDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UsersSecurity registerUser(UsersDto user) {
        UsersDto userFromDb = restTemplate.postForObject(baseURL + "user", user, UsersDto.class);
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        List<Roles> role = List.of(rolesDao.findByRoleName("Client"));
        UsersSecurity userSecurity = UsersSecurity
                .builder()
                .login(user.getLogin())
                .password(encryptedPassword)
                .role(role)
                .userId(userFromDb.getId())
                .build();
        return securityService.createUserSecurity(userSecurity);
    }
}
