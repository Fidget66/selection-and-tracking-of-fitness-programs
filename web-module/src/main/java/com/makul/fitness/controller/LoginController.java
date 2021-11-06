package com.makul.fitness.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String get(Model model) {
        model.addAttribute("title", "Форма входа");
        return "login";
    }

    @GetMapping("/403")
    public String error403() {
        return "403";
    }

    @GetMapping("/")
    public String main(Model model){
        return "Index";
    }
}
