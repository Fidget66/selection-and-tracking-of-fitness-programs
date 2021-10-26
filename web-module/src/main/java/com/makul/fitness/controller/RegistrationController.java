package com.makul.fitness.controller;

import com.makul.fitness.dto.UsersDto;
import com.makul.fitness.service.api.RegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import javax.validation.Valid;

@Controller
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping("/registration")
    public String getRegistrationForm(){
        return "registrationForm";
    }

    @PostMapping("/registration/user")
    public String registerUser(@Valid UsersDto user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("usersDto",user);
            return "registrationForm";}
        registrationService.registerUser(user);
        return "redirect:/login";
    }

    @ModelAttribute("usersDto")
    private UsersDto prepareUsersModel() {
        return new UsersDto();
    }
}
