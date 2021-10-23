package com.makul.fitness.controller;

import com.makul.fitness.dto.CategoryOfFitnessProgramDto;
import com.makul.fitness.dto.FitnessProgramDto;
import com.makul.fitness.dto.ReviewDto;
import com.makul.fitness.dto.UsersDto;
import com.makul.fitness.service.api.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/admin/category")
    public String returnCategoryForm(){
        return "admin/createCategory";
    }

    @PostMapping("/admin/category")
    public String addCategory(@ModelAttribute CategoryOfFitnessProgramDto category){
        adminService.createCategory(category);
        return "redirect:/";
    }

    @GetMapping("admin/category/program")
    public String chooseCategory(Model model){
        List <CategoryOfFitnessProgramDto> categories = adminService.readCategoryOfFitnessProgram();
        model.addAttribute("all",categories);
        return "admin/categoryList";
    }

    @GetMapping("admin/category/{id}/program")
    public String returnFitnessProgrammForm(HttpServletRequest request,
                                            Model model){
        model.addAttribute("req", request.getHeader("Referer"));
        return "admin/fitnessProgram";
    }

    @PostMapping("admin/category/{id}/program")
    public String addFitnessProgram(@ModelAttribute FitnessProgramDto fitnessProgramDto,
                                    @PathVariable("id") long categoryId){
        adminService.createFitnessProgram(categoryId, fitnessProgramDto);
        return "redirect:/";
    }

    @GetMapping("admin/blocking")
    public String returnFindUserForm(HttpServletRequest request,
                                            Model model){
        model.addAttribute("req", request.getHeader("Referer"));
        return "admin/findUser";
    }

    @GetMapping("admin/blocking/users")
    public String findUsersByName(@RequestParam String name, @RequestParam String lastName, HttpServletRequest request,
                                     Model model){
        List<UsersDto> usersList = adminService.readUsersByNameLastName(name, lastName);
        model.addAttribute("users",usersList);
        model.addAttribute("req", request.getHeader("Referer"));
        return "admin/users";
    }

    @GetMapping("admin/blocking/users/{id}")
    public String blockUser(@PathVariable("id") long userId){
        adminService.blockUser(userId);
        return "redirect:/";
    }


    @GetMapping("admin/category/program/reviews")
    public String chooseCategoryForReviews(Model model){
        List <CategoryOfFitnessProgramDto> categories = adminService.readCategoryOfFitnessProgram();
        model.addAttribute("all",categories);
        return "admin/categoriesList";
    }

    @GetMapping("admin/category/{id}/program/reviews")
    public String viewFitnessProgram(@PathVariable ("id") long categoryId, Model model){
        List<FitnessProgramDto> programs = adminService.readListFitnessProgram(categoryId);
        model.addAttribute("allPrograms", programs);
        return "admin/fitnessPrograms";
    }

    @GetMapping("admin/category/program/{id}/reviews")
    public String viewReviews (@PathVariable ("id") long programId, Model model, HttpServletRequest request){
        List<ReviewDto> reviewList = adminService.readListReviews(programId);
        model.addAttribute("reviewList", reviewList);
        model.addAttribute("req",request.getHeader("Referer"));
        return "admin/reviews";
    }

    @GetMapping("/admin/category/program/reviews/{id}")
    public String returnEditReviewForm(@PathVariable ("id") long id, Model model){
        model.addAttribute("reviewDto", adminService.readReview(id));
        return "admin/review";
    }

    @PostMapping("/admin/category/program/reviews/{id}")
    public String updateReview(@ModelAttribute ReviewDto review){
        adminService.updateReview(review);
        return "redirect:/";
    }

    @ModelAttribute("categoryDto")
    private CategoryOfFitnessProgramDto prepareCategoryModel() {
        return new CategoryOfFitnessProgramDto();
    }

    @ModelAttribute("fitnessProgramDto")
    private FitnessProgramDto prepareFitnessProgramModel() {
        return new FitnessProgramDto();
    }

    @ModelAttribute("reviewDto")
    private ReviewDto prepareReviewModel() {
        return new ReviewDto();
    }
}
