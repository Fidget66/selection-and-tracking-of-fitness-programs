package com.makul.fitness.service;

import com.makul.fitness.dao.UsersSecurityDao;
import com.makul.fitness.dto.CategoryOfFitnessProgramDto;
import com.makul.fitness.dto.FitnessProgramDto;
import com.makul.fitness.service.api.ClientService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private final RestTemplate restTemplate;
    private final UsersSecurityDao securityDao;
    private final String baseURL = "http://localhost:8124/fitnessDB-app/";

    public ClientServiceImpl(RestTemplate restTemplate, UsersSecurityDao securityDao) {
        this.restTemplate = restTemplate;
        this.securityDao = securityDao;
    }

    @Override
    public List<CategoryOfFitnessProgramDto> getCategoryOfFitnessProgram() {
        List <CategoryOfFitnessProgramDto> categories = Arrays.asList(restTemplate.getForObject(baseURL +
                        "fitness/categories", CategoryOfFitnessProgramDto[].class));
        return categories;
    }

    @Override
    public List<FitnessProgramDto> getCategoryListFitnessProgram(long categoryId) {
        List <FitnessProgramDto> programs = Arrays.asList(restTemplate.getForObject(baseURL +
                        "category/"+ categoryId+ "/program/fitness", FitnessProgramDto[].class));
        return programs;
    }

    @Override
    public FitnessProgramDto getFitnessProgram(long programId) {
        FitnessProgramDto program = restTemplate.getForObject(baseURL+"/program/fitness/" + programId,
                FitnessProgramDto.class);
        return program;
    }

    @Override
    public List<FitnessProgramDto> getFitnessProgramWithRestrictions(int duration) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        long userId = securityDao.findByLogin(login).getUserId();
        List <FitnessProgramDto> programs = Arrays.asList(restTemplate.getForObject(baseURL +
                "user/"+userId+"/program/fitness/"+duration, FitnessProgramDto[].class));
        return programs;
    }


}
