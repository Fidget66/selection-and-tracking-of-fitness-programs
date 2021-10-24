package com.makul.fitness.controller;

import com.makul.fitness.dto.UsersDto;
import com.makul.fitness.service.api.RegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping("registration")
    public String getRegistrationForm(){
        return "registrationForm";
    }

    @PostMapping("registration/user")
    public String registerUser(@ModelAttribute UsersDto user) {
        registrationService.registerUser(user);
        return "redirect:/login";
    }

    @ModelAttribute("userDto")
    private UsersDto prepareUsersModel() {
        return new UsersDto();
    }
}
