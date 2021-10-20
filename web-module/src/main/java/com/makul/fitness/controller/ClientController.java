package com.makul.fitness.controller;

import com.makul.fitness.dto.CategoryOfFitnessProgramDto;
import com.makul.fitness.dto.FitnessProgramDto;
import com.makul.fitness.service.api.ClientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/category/fitness")
    public String viewCategory(Model model){
        List<CategoryOfFitnessProgramDto> categories = clientService.getCategoryOfFitnessProgram();
        model.addAttribute("all", categories);
        return "categoryOfFitness";
    }

    @GetMapping("/category/fitness/{id}/program/")
    public String viewFitnessProgram(@PathVariable ("id") long id, Model model){
        List<FitnessProgramDto> programs = clientService.getCategoryListFitnessProgram(id);
        model.addAttribute("allPrograms", programs);
        return "fitnessPrograms";
    }

    @GetMapping("/category/fitness/program/{id}/description")
    public String viewFitnessProgramDescription(@PathVariable ("id") long id, Model model){
        FitnessProgramDto program = clientService.getFitnessProgram(id);
        model.addAttribute("fitProg", program);
        return "fitnessProgramDescription";
    }

    @GetMapping("/fitness/program/restrictions")
    public String returnFormWithRestriction(Model model){
        return "fitnessProgramAddRestriction";
    }

    @GetMapping("/fitness/program/restrictions/duration")
    public String viewFitnessProgramWithRestrictions(@RequestParam("durationLim") int duration, Model model){
        List<FitnessProgramDto> programList = clientService.getFitnessProgramWithRestrictions(duration);
        model.addAttribute("allPrograms", programList);
        return "fitnessPrograms";
    }
}
