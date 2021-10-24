package com.makul.fitness.service;

import com.makul.fitness.dto.CategoryOfFitnessProgramDto;
import com.makul.fitness.dto.FitnessProgramDto;
import com.makul.fitness.dto.ReviewDto;
import com.makul.fitness.dto.UsersDto;
import com.makul.fitness.service.api.AdminService;
import com.makul.fitness.service.api.UsersSecurityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    private final RestTemplate restTemplate;
    private final UsersSecurityService securityService;
    private final String baseURL = "http://localhost:8124/fitnessDB-app/";

    public AdminServiceImpl(RestTemplate restTemplate, UsersSecurityService securityService) {
        this.restTemplate = restTemplate;
        this.securityService = securityService;
    }

    @Override
    public void createCategory(CategoryOfFitnessProgramDto category) {
        restTemplate.postForLocation(baseURL + "fitness/category",category);
    }

    @Override
    public List<CategoryOfFitnessProgramDto> readCategoryOfFitnessProgram() {
        List <CategoryOfFitnessProgramDto> categories = Arrays.asList(restTemplate.getForObject(baseURL +
                "fitness/categories", CategoryOfFitnessProgramDto[].class));
        return categories;
    }

    @Override
    public void createFitnessProgram(long categoryId, FitnessProgramDto fitnessProgramDto) {
        restTemplate.postForLocation(baseURL + "category/" + categoryId + "/program/fitness",fitnessProgramDto);
    }

    @Override
    public List<UsersDto> readUsersByNameLastName(String name, String lastName) {
        List<UsersDto> usersList = Arrays.asList(restTemplate
                .getForObject(baseURL + "user/" + name + "/" + lastName, UsersDto[].class));
        return usersList;
    }

    @Override
    @Transactional
    public void blockUser(long userId) {
       securityService.blockUser(userId);
    }

    @Override
    public List<FitnessProgramDto> readListFitnessProgram(long categoryId) {
        List <FitnessProgramDto> programs = Arrays.asList(restTemplate.getForObject(baseURL +
                "category/"+ categoryId+ "/program/fitness", FitnessProgramDto[].class));
        return programs;
    }

    @Override
    public List<ReviewDto> readListReviews(long fitnessProgramId) {
        FitnessProgramDto program = restTemplate.getForObject(baseURL+"program/fitness/" + fitnessProgramId,
                FitnessProgramDto.class);
        return program.getReviews();
    }

    @Override
    public ReviewDto readReview(long reviewId) {
        ReviewDto reviewDto = restTemplate.getForObject(baseURL+"review/" + reviewId,
                ReviewDto.class);
        return reviewDto;
    }

    @Override
    public void updateReview(ReviewDto review) {
        restTemplate.put(baseURL+"review", review);
    }
}
