package com.makul.fitness.service;

import com.makul.fitness.dao.RolesDao;
import com.makul.fitness.dto.CategoryOfFitnessProgramDto;
import com.makul.fitness.dto.FitnessProgramDto;
import com.makul.fitness.dto.ReviewDto;
import com.makul.fitness.dto.UsersDto;
import com.makul.fitness.model.Roles;
import com.makul.fitness.service.api.AdminService;
import com.makul.fitness.service.api.UsersSecurityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {
    private final RestTemplate restTemplate;
    private final UsersSecurityService securityService;
    private final RolesDao rolesDao;
    private final String baseURL = "http://fitnessApp:8124/fitnessDB-app/";

    public AdminServiceImpl(RestTemplate restTemplate, UsersSecurityService securityService, RolesDao rolesDao) {
        this.restTemplate = restTemplate;
        this.securityService = securityService;
        this.rolesDao = rolesDao;
    }

    @Override
    public void createCategory(CategoryOfFitnessProgramDto category) {
        restTemplate.postForLocation(baseURL + "fitness/category",category);
    }

    @Override
    public List<CategoryOfFitnessProgramDto> readCategoryOfFitnessProgram() {
        return Arrays.asList(restTemplate.getForObject(baseURL +
                "fitness/category", CategoryOfFitnessProgramDto[].class));
    }

    @Override
    public void createFitnessProgram(long categoryId, FitnessProgramDto fitnessProgramDto) {
        restTemplate.postForLocation(baseURL + "category/" + categoryId + "/program/fitness",fitnessProgramDto);
    }

    @Override
    public List<UsersDto> readUnlockedUsersByNameLastName(String name, String lastName) {
        List<UsersDto> usersList = Arrays.asList(restTemplate
                .getForObject(baseURL + "user/" + name + "/" + lastName, UsersDto[].class));
        for (UsersDto user:usersList) {
            user.setRoles(securityService.readByUserId(user.getId()).getRole());
        }
        Roles role = rolesDao.findByRoleName("Client");
        usersList = usersList
                .stream()
                .filter(usersDto -> usersDto.getRoles().contains(role))
                .collect(Collectors.toList());
        return usersList;
    }

    @Override
    public List<UsersDto> readBlockedUsersByNameLastName(String name, String lastName) {
        List<UsersDto> usersList = Arrays.asList(restTemplate
                .getForObject(baseURL + "user/" + name + "/" + lastName, UsersDto[].class));
        for (UsersDto user:usersList) {
            user.setRoles(securityService.readByUserId(user.getId()).getRole());
        }
        Roles role = rolesDao.findByRoleName("Blocked");
        usersList = usersList
                .stream()
                .filter(usersDto -> usersDto.getRoles().contains(role))
                .collect(Collectors.toList());
        return usersList;
    }

    @Override
    @Transactional
    public void blockUser(long userId) {
       securityService.blockUser(userId);
    }

    @Override
    public void unblockUser(long userId) {
        securityService.unblockUser(userId);
    }

    @Override
    public List<FitnessProgramDto> readListFitnessProgram(long categoryId) {
        return Arrays.asList(restTemplate.getForObject(baseURL +
                "category/"+ categoryId+ "/program/fitness", FitnessProgramDto[].class));
    }

    @Override
    public List<ReviewDto> readListReviews(long fitnessProgramId) {
        FitnessProgramDto program = restTemplate.getForObject(baseURL+"program/fitness/" + fitnessProgramId,
                FitnessProgramDto.class);
        return program.getReviews();
    }

    @Override
    public ReviewDto readReview(long reviewId) {
        return restTemplate.getForObject(baseURL+"review/" + reviewId,
                ReviewDto.class);
    }

    @Override
    public void updateReview(ReviewDto review) {
        restTemplate.put(baseURL+"review", review);
    }
}
