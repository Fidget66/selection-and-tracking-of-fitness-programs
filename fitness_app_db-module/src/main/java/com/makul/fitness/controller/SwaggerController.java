package com.makul.fitness.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SwaggerController {
    @GetMapping("/swagger-ui")
    public String swaggerUi() {
        return "redirect:/swagger-ui.html";
    }
}