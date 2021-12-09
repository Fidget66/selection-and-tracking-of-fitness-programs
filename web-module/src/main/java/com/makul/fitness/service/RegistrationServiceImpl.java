package com.makul.fitness.service;

import com.makul.fitness.dao.RolesDao;
import com.makul.fitness.dto.UsersDto;
import com.makul.fitness.exceptions.RegistrationException;
import com.makul.fitness.model.Roles;
import com.makul.fitness.model.UsersSecurity;
import com.makul.fitness.service.api.RegistrationService;
import com.makul.fitness.service.api.UsersSecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final RestTemplate restTemplate;
    private final UsersSecurityService securityService;
    private final RolesDao rolesDao;
    private final BCryptPasswordEncoder passwordEncoder;
    private final String baseURL = "http://fitnessApp:8124/fitnessDB-app/";

    @Override
    @Transactional
    public UsersSecurity registerUser(UsersDto user) {
        UsersDto userFromDb = restTemplate.postForObject(baseURL + "user", user, UsersDto.class);
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        List<Roles> role = List.of(rolesDao.findByRoleName("Client"));
        if (userFromDb.getId()<1) throw new RegistrationException();
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
