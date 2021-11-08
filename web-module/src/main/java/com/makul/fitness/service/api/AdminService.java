package com.makul.fitness.service.api;

import com.makul.fitness.dto.CategoryOfFitnessProgramDto;
import com.makul.fitness.dto.FitnessProgramDto;
import com.makul.fitness.dto.ReviewDto;
import com.makul.fitness.dto.UsersDto;
import java.util.List;

public interface AdminService {
    void createCategory(CategoryOfFitnessProgramDto category);
    List<CategoryOfFitnessProgramDto> readCategoryOfFitnessProgram();
    void createFitnessProgram(long categoryId, FitnessProgramDto fitnessProgramDto);
    List <UsersDto> readUnlockedUsersByNameLastName(String name, String lastName);
    List <UsersDto> readBlockedUsersByNameLastName(String name, String lastName);
    void blockUser (long userId);
    List <FitnessProgramDto> readListFitnessProgram(long categoryId);
    List <ReviewDto> readListReviews(long fitnessProgramId);
    ReviewDto readReview(long id);
    void updateReview(ReviewDto review);
    void unblockUser(long userId);
}
